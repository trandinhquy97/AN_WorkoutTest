package com.example.annewandroid2023.domain.app

import io.reactivex.Scheduler

interface SchedulerProviderInterface {
    fun computation(): Scheduler
    fun io(): Scheduler
    fun ui(): Scheduler
}