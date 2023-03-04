package com.example.annewandroid2023.data.remote

import com.example.annewandroid2023.data.remote.dto.ResponseDto
import io.reactivex.Single
import retrofit2.http.GET

interface AppApi {
    @GET("/workout")
    fun getWorkoutDates(): Single<ResponseDto>
}