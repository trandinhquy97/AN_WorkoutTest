package com.example.annewandroid2023.data.repository

import com.example.annewandroid2023.data.local.AppDatabase
import com.example.annewandroid2023.data.local.entity.WorkoutDateEntity
import com.example.annewandroid2023.data.local.entity.WorkoutEntity
import com.example.annewandroid2023.data.remote.AppApi
import com.example.annewandroid2023.data.remote.dto.WorkoutDateDto
import com.example.annewandroid2023.data.remote.dto.WorkoutDto
import com.example.annewandroid2023.domain.repository.MainRepository
import io.reactivex.Single
import javax.inject.Inject

class MainRepositoryImpl @Inject constructor(
    private val api: AppApi,
    private val appDatabase: AppDatabase
) : MainRepository {
    override fun getWorkoutDates(): Single<List<WorkoutDateDto>> {
        return appDatabase.workoutDateDao.getWorkoutDateWithWorkout()
            .map { list ->
                return@map list.map {
                    return@map WorkoutDateDto(it.workoutDate.dateId, it.workoutList.map { workout ->
                        WorkoutDto(
                            workout.workoutId,
                            workout.status,
                            workout.title,
                            workout.exercisesCount
                        )
                    })
                }
            }.flatMap {
                if (it.isEmpty())
                    return@flatMap api.getWorkoutDates().map {
                        return@map it.data
                    }.doOnSuccess {
                        List(it.size) { index ->
                            appDatabase.workoutDateDao.insertDate(WorkoutDateEntity(it[index].id))
                            appDatabase.workoutDateDao.insertWorkoutAll(it[index].assignments.map { workout ->
                                return@map WorkoutEntity(
                                    workoutId = workout.id,
                                    workoutDateId = it[index].id,
                                    status = workout.status,
                                    title = workout.title,
                                    exercisesCount = workout.exercisesCount
                                )
                            })
                        }
                    }
                else Single.just(it)
            }
    }

    override fun updateWorkout(id: String, status: Int): Single<Int> {
        return appDatabase.workoutDateDao.updateWorkout(id, status)
    }
}