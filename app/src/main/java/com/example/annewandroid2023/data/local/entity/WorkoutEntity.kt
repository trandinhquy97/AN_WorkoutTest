package com.example.annewandroid2023.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class WorkoutEntity(
    @PrimaryKey var workoutId: String,
    var workoutDateId: String,
    var status: Int,
    var title: String,
    var exercisesCount: Int
)