package com.example.annewandroid2023.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.annewandroid2023.data.local.dao.WorkoutDateDao
import com.example.annewandroid2023.data.local.entity.WorkoutDateEntity
import com.example.annewandroid2023.data.local.entity.WorkoutEntity

@Database(
    entities = [WorkoutDateEntity::class, WorkoutEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract val workoutDateDao: WorkoutDateDao
}