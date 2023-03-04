package com.example.annewandroid2023.presentation.workout_list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.annewandroid2023.data.remote.wrapper.AppError
import com.example.annewandroid2023.domain.model.WorkoutDate
import com.example.annewandroid2023.domain.usecase.GetWorkoutDateUseCase
import com.example.annewandroid2023.domain.usecase.UpdateWorkoutUseCase
import com.example.annewandroid2023.domain.usecase.base.SingleStreamUseCaseObserver
import com.example.annewandroid2023.presentation.base.BaseViewModel
import com.example.annewandroid2023.utils.SingleLiveEvent
import com.example.annewandroid2023.utils.fallBackToUnclassifiedError
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxkotlin.addTo
import javax.inject.Inject

@HiltViewModel
class WorkoutListViewModel @Inject constructor(
    private val getWorkoutDateUseCase: GetWorkoutDateUseCase,
    private val updateWorkoutUseCase: UpdateWorkoutUseCase,
) : BaseViewModel() {

    private val getCoinObserver = SingleStreamUseCaseObserver<List<WorkoutDate>>()
    private val updateObserver = SingleStreamUseCaseObserver<Int>()

    private val _progress = MutableLiveData(false)
    val progress: LiveData<Boolean> = _progress

    private val _error: MutableLiveData<AppError> = SingleLiveEvent()
    val error: LiveData<AppError> = _error

    private val _workoutDates: MutableLiveData<List<WorkoutDate>> = MutableLiveData()
    val workoutDates: LiveData<List<WorkoutDate>> = _workoutDates

    init {
        setUpObserver()
        getCoinList()
    }

    private fun setUpObserver() {
        getCoinObserver.processing.subscribe {
            _progress.value = it
        }.addTo(compositeDisposable)
        getCoinObserver.failed.fallBackToUnclassifiedError().subscribe {
            _error.value = it
        }.addTo(compositeDisposable)
        getCoinObserver.succeeded.subscribe {
            _workoutDates.value = it
        }.addTo(compositeDisposable)

        updateObserver.failed.fallBackToUnclassifiedError().subscribe {
            _error.value = it
        }.addTo(compositeDisposable)
    }

    private fun getCoinList() {
        getCoinObserver.invokeUseCase(getWorkoutDateUseCase, Unit)
    }

    fun changeDataInViewModel(id: String, mark: Int) {
        val list: List<WorkoutDate>? = _workoutDates.value

        updateObserver.invokeUseCase(updateWorkoutUseCase, UpdateWorkoutUseCase.Param(id, mark))

        list?.let {
            val result: MutableList<WorkoutDate> = list.toMutableList()
            result.forEach {
                it.assignments.forEach {
                    if (it.id == id) {
                        it.status = mark
                    }
                }
            }
            _workoutDates.value = result
        }
    }
}