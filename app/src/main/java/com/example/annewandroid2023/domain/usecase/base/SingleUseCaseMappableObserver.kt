package com.example.annewandroid2023.domain.usecase.base

import com.example.annewandroid2023.data.remote.wrapper.AppError
import com.example.annewandroid2023.data.remote.wrapper.Retry
import io.reactivex.Observable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject

class ActionAlreadyPerformingException : Throwable() {
    override val message: String
        get() = "the action to execute is performing now"
}

/**
 * You can retrieve `singleObserverIfNotPerformed` and `singleObserver` as you like.
 * Used when return parameter need to be mapped.
 * This mapper should not be from API response to data entity.
 * But from data entity to property or some to fit view representation.
 * You can set mapper fun as a parameter for each observer retrieval fun.
 */
open class SingleUseCaseMappableObserver<SourceType: Any, ReturnType : Any> {
    // note: `BehaviorSubject` emits previous state on subscribe
    private val _processing = BehaviorSubject.create<Boolean>()
    val processing: Observable<Boolean> = _processing

    private val _succeeded = PublishSubject.create<ReturnType>()
    val succeeded: Observable<ReturnType> = _succeeded

    private val _failed = PublishSubject.create<Throwable>()
    val failed: Observable<Throwable> = _failed

    // / used when you don't want to observe at multiple points from one usecase.
    fun singleObserverIfNotPerformed(retry: Retry? = null, mapper: (SourceType) -> ReturnType):
            DisposableSingleObserver<SourceType>? {
        if (_processing.value == true) {
            _failed.onNext(ActionAlreadyPerformingException())
            return null
        }
        _processing.onNext(true)

        return object : DisposableSingleObserver<SourceType>() {

            override fun onError(e: Throwable) {
                _processing.onNext(false)
                _failed.onNext(e.also { (it as? AppError)?.retry = retry })
            }

            override fun onSuccess(t: SourceType) {
                _processing.onNext(false)
                _succeeded.onNext(mapper(t))
            }
        }
    }

    // / used when you want to observe simple single observables
    fun singleObserver(retry: Retry?, mapper: (SourceType) -> ReturnType):
            DisposableSingleObserver<SourceType> {
        return object : DisposableSingleObserver<SourceType>() {
            override fun onSuccess(t: SourceType) {
                _succeeded.onNext(mapper(t))
            }

            override fun onError(e: Throwable) {
                _failed.onNext(e.also { (it as? AppError)?.retry = retry })
            }
        }
    }

    // used when you don't want to observe at multiple points from one usecase.
    fun singleObserverLast(retry: Retry? = null, mapper: (SourceType) -> ReturnType):
            DisposableSingleObserver<SourceType> {
        _processing.onNext(true)

        return object : DisposableSingleObserver<SourceType>() {
            override fun onSuccess(t: SourceType) {
                _processing.onNext(false)
                _succeeded.onNext(mapper(t))
            }

            override fun onError(e: Throwable) {
                _processing.onNext(false)
                _failed.onNext(e.also { (it as? AppError)?.retry = retry })
            }
        }
    }
}
