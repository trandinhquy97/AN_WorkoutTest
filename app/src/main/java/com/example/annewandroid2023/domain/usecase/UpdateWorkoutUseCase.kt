package com.example.annewandroid2023.domain.usecase

import com.example.annewandroid2023.domain.app.SchedulerProviderInterface
import com.example.annewandroid2023.domain.repository.MainRepository
import com.example.annewandroid2023.domain.usecase.base.SingleStreamUseCase
import io.reactivex.Single
import javax.inject.Inject

class UpdateWorkoutUseCase @Inject constructor(
    private val repository: MainRepository,
    schedulerProvider: SchedulerProviderInterface
) : SingleStreamUseCase<Int, UpdateWorkoutUseCase.Param>(schedulerProvider) {

    data class Param(
        val id: String,
        val status: Int,
    )

    override fun buildUseCaseObservable(params: Param): Single<Int> {
        return repository.updateWorkout(params.id, params.status)
    }
}