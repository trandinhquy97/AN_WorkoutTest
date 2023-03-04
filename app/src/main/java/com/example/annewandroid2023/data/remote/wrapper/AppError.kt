package com.example.annewandroid2023.data.remote.wrapper

import com.example.annewandroid2023.data.remote.ApiEnum

typealias Retry = () -> Unit

data class AppError(val api: ApiEnum, val throwable: Throwable) : RuntimeException(throwable) {

    var retry: Retry? = null
}