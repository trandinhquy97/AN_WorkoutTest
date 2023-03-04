package com.example.annewandroid2023.utils

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import com.example.annewandroid2023.data.remote.ApiEnum
import com.example.annewandroid2023.data.remote.wrapper.AppError
import io.reactivex.Observable

fun <T> LifecycleOwner.observe(liveData: LiveData<T>, action: (t: T) -> Unit) {
    liveData.observe(this) { it?.let { t -> action(t) } }
}

fun <T : Throwable> Observable<T>.fallBackToUnclassifiedError(): Observable<AppError> = map {
    if (it is AppError) it else AppError(ApiEnum.UnClassified, it)
}