package com.example.annewandroid2023.domain.usecase.base

import android.util.Log
import com.example.annewandroid2023.domain.app.SchedulerProviderInterface
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableSingleObserver


/**
 * Abstract class for a UseCase that returns an instance of a [Single].
 */
abstract class SingleStreamUseCase<T, in Params> constructor(
    private val schedulerProvider: SchedulerProviderInterface
) {

    private val disposables = CompositeDisposable()

    /**
     * Builds a [Single] which will be used when the current [SingleStreamUseCase] is executed.
     */
    protected abstract fun buildUseCaseObservable(params: Params): Single<T>

    /**
     * Executes the current use case.
     */
    open fun execute(singleObserver: DisposableSingleObserver<T>, params: Params) {
        Log.d("DEBUG","${object {}.javaClass.enclosingMethod?.name}, $params")
        val single = this.buildUseCaseObservable(params)
            .doOnError { Log.d("ERROR", it.message.orEmpty()) }
            .subscribeOn(schedulerProvider.io())
            .observeOn(schedulerProvider.ui()) as Single<T>
        addDisposable(single.subscribeWith(singleObserver))
    }

    /**
     * Dispose from current [CompositeDisposable].
     */
    fun dispose() {
        if (!disposables.isDisposed) disposables.dispose()
    }

    fun removeSubscribes() {
        disposables.clear()
    }

    /**
     * Dispose from current [CompositeDisposable].
     */
    private fun addDisposable(disposable: Disposable) {
        disposables.add(disposable)
    }
}
