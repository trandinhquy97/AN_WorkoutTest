package com.example.annewandroid2023.presentation.workout_list.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.annewandroid2023.databinding.SubItemBinding
import com.example.annewandroid2023.domain.model.Workout
import java.util.*

class SubItemAdapter internal constructor(
    subItemList: List<Workout>,
    private val date: Date,
    private val onClick: (id: String, mark: Int) -> Unit
) :
    RecyclerView.Adapter<SubItemAdapter.SubItemViewHolder>() {
    private var subItemList: List<Workout>

    init {
        this.subItemList = subItemList
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): SubItemViewHolder {
        val binding =
            SubItemBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return SubItemViewHolder(binding)
    }

    override fun onBindViewHolder(subItemViewHolder: SubItemViewHolder, i: Int) {
        val subItem: Workout = subItemList[i]
        subItemViewHolder.binding.model = subItem
        subItemViewHolder.binding.date = date
        subItemViewHolder.binding.root.setOnClickListener {
            if (compareDate(date, Calendar.getInstance().time) != 1) {
                onClick.invoke(subItem.id, reverseStatus(subItem.status))
            }
        }
    }

    override fun getItemCount(): Int {
        return subItemList.size
    }

    private fun reverseStatus(status: Int): Int {
        if (status == 2) return 0
        if (status == 0) return 2
        return 1
    }

    fun setList(list: List<Workout>) {
        subItemList = list
        notifyDataSetChanged()
    }

    inner class SubItemViewHolder(val binding: SubItemBinding) :
        RecyclerView.ViewHolder(binding.root)
}