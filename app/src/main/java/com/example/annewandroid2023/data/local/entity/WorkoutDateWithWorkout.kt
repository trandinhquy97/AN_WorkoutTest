package com.example.annewandroid2023.data.local.entity

import androidx.room.Embedded
import androidx.room.Relation

data class WorkoutDateWithWorkout(
    @Embedded val workoutDate: WorkoutDateEntity,
    @Relation(
        parentColumn = "dateId",
        entityColumn = "workoutDateId"
    )
    val workoutList: List<WorkoutEntity>
)