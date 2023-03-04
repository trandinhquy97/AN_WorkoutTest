package com.example.annewandroid2023.domain.usecase

import com.example.annewandroid2023.domain.app.SchedulerProviderInterface
import com.example.annewandroid2023.domain.model.WorkoutDate
import com.example.annewandroid2023.domain.repository.MainRepository
import com.example.annewandroid2023.domain.usecase.base.SingleStreamUseCase
import io.reactivex.Single
import javax.inject.Inject

class GetWorkoutDateUseCase @Inject constructor(
    private val repository: MainRepository,
    schedulerProvider: SchedulerProviderInterface
) : SingleStreamUseCase<List<WorkoutDate>, Unit>(schedulerProvider) {

    override fun buildUseCaseObservable(params: Unit): Single<List<WorkoutDate>> {
        return repository.getWorkoutDates().map {
            it.map { workoutDateDto ->
                workoutDateDto.toWorkoutDate()
            }
        }
    }
}