package com.example.annewandroid2023.data.remote.dto

import com.example.annewandroid2023.domain.model.WorkoutDate
import com.google.gson.annotations.SerializedName

data class WorkoutDateDto(
    @SerializedName("_id") val id: String,
    val assignments: List<WorkoutDto>
) {
    fun toWorkoutDate(): WorkoutDate {
        return WorkoutDate(
            id,
            assignments.map {
                it.toWorkout()
            }
        )
    }
}