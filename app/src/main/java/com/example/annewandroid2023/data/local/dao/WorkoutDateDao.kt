package com.example.annewandroid2023.data.local.dao

import androidx.room.*
import com.example.annewandroid2023.data.local.entity.WorkoutDateEntity
import com.example.annewandroid2023.data.local.entity.WorkoutDateWithWorkout
import com.example.annewandroid2023.data.local.entity.WorkoutEntity
import io.reactivex.Single

@Dao
interface WorkoutDateDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertDate(date: WorkoutDateEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertWorkoutAll(workoutList: List<WorkoutEntity>)

    @Transaction
    @Query("SELECT * FROM WorkoutDateEntity")
    fun getWorkoutDateWithWorkout(): Single<List<WorkoutDateWithWorkout>>

    @Query("UPDATE WorkoutEntity SET status = :status WHERE workoutId = :id")
    fun updateWorkout(id: String, status: Int): Single<Int>
}