package com.example.annewandroid2023.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class WorkoutDateEntity(
    @PrimaryKey val dateId: String,
)