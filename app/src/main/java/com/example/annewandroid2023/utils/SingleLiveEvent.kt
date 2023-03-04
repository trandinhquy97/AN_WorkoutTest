package com.example.annewandroid2023.utils

import android.util.Log
import androidx.annotation.MainThread
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import java.util.concurrent.atomic.AtomicBoolean

class SingleLiveEvent<T>() : MutableLiveData<T>() {

    private val mPending = AtomicBoolean(false)

    companion object {
        private const val TAG = "SingleLiveEvent"
    }

    constructor(initialValue: T) : this() {
        value = initialValue
    }

    @MainThread
    override fun observe(owner: LifecycleOwner, observer: Observer<in T>) {
        if (hasActiveObservers()) {
            Log.d(
                TAG,
                "$TAG Multiple observers registered but only one will be notified of changes."
            )
        }

        super.observe(
            owner
        ) {
            if (mPending.compareAndSet(true, false)) {
                observer.onChanged(it)
            }
        }
    }

    @MainThread
    override fun setValue(t: T?) {
        mPending.set(true)
        super.setValue(t)
    }

    /**
     * Used for cases where T is Void, to make calls cleaner.
     */
    @MainThread
    fun call() {
        value = null
    }
}