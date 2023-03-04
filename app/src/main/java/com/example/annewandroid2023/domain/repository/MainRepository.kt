package com.example.annewandroid2023.domain.repository

import com.example.annewandroid2023.data.remote.dto.WorkoutDateDto
import io.reactivex.Single

interface MainRepository {
    fun getWorkoutDates(): Single<List<WorkoutDateDto>>
    fun updateWorkout(id: String, status: Int): Single<Int>
}