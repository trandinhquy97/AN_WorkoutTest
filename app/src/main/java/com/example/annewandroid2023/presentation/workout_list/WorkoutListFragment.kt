package com.example.annewandroid2023.presentation.workout_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.annewandroid2023.data.remote.wrapper.AppError
import com.example.annewandroid2023.databinding.FragmentWorkoutListBinding
import com.example.annewandroid2023.domain.model.WorkoutDate
import com.example.annewandroid2023.presentation.workout_list.adapter.ItemAdapter
import com.example.annewandroid2023.utils.observe
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WorkoutListFragment : Fragment() {

    private var _binding: FragmentWorkoutListBinding? = null
    val binding get() = _binding!!

    private val viewModel: WorkoutListViewModel by viewModels()
    private lateinit var itemAdapter: ItemAdapter

    private val onClick: (id: String, mark: Int) -> Unit = { id, mark ->
        viewModel.changeDataInViewModel(id, mark)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWorkoutListBinding.inflate(inflater, container, false)

        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeViewModel()
        bindListData()
    }

    private fun observeViewModel() {
        observe(viewModel.workoutDates, ::bindListData)
        observe(viewModel.error, ::handleError)
    }

    private fun bindListData(workoutDates: List<WorkoutDate> = listOf()) {
        if (this::itemAdapter.isInitialized) {
            itemAdapter.setList(workoutDates)
        } else {
            itemAdapter = ItemAdapter(workoutDates, onClick).apply {
                this.setHasStableIds(true)
            }
            binding.rvItem.adapter = itemAdapter
        }
    }

    private fun handleError(error: AppError) {
        Toast.makeText(context, error.message, Toast.LENGTH_LONG).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}