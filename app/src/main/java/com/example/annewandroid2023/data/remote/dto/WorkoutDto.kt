package com.example.annewandroid2023.data.remote.dto

import com.example.annewandroid2023.domain.model.Workout
import com.google.gson.annotations.SerializedName

data class WorkoutDto (
    @SerializedName("_id") var id: String,
    var status: Int,
    var title: String,
    @SerializedName("exercises_count") var exercisesCount: Int
) {
    fun toWorkout(): Workout {
        return Workout(
            id = id,
            status = status,
            title = title,
            exercisesCount = exercisesCount
        )
    }
}