package com.example.annewandroid2023.domain.usecase.base

import com.example.annewandroid2023.data.remote.wrapper.Retry
import io.reactivex.Observable
import io.reactivex.observers.DisposableSingleObserver

/**
 * Used as a observer for SingleUseCase.
 * Especially when there are no need to map (T is going to be same source/return parameter type)
 */
class SingleStreamUseCaseObserver<T: Any> {
    private val core: SingleUseCaseMappableObserver<T, T> = SingleUseCaseMappableObserver()
    val processing: Observable<Boolean> = core.processing
    val succeeded: Observable<T> = core.succeeded
    val failed: Observable<Throwable> = core.failed

    private fun singleObserverIfNotPerformed(retry: Retry?): DisposableSingleObserver<T>? =
        core.singleObserverIfNotPerformed(retry) { it }

    private fun singleObserver(retry: Retry?): DisposableSingleObserver<T>? =
        core.singleObserver(retry) { it }

    // invoke singleObserverIfNotPerformed()
    fun <TArg> invokeUseCase(
        useCase: SingleStreamUseCase<T, TArg>,
        params: TArg,
        retry: Retry? = { invokeUseCase(useCase, params) }
    ) {
        singleObserverIfNotPerformed(retry)?.let { useCase.execute(it, params) }
    }
}