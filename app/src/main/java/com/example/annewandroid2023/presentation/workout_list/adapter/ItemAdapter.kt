package com.example.annewandroid2023.presentation.workout_list.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.RecycledViewPool
import com.example.annewandroid2023.databinding.ItemBinding
import com.example.annewandroid2023.domain.model.WorkoutDate
import java.util.*

class ItemAdapter internal constructor(
    private var itemList: List<WorkoutDate>,
    private val onClick: (id: String, mark: Int) -> Unit
) :
    RecyclerView.Adapter<ItemAdapter.ItemViewHolder>() {
    private val viewPool = RecycledViewPool()

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ItemViewHolder {
        val binding =
            ItemBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, i: Int) {
        val cal = Calendar.getInstance()
        var dayOfWeek = cal[Calendar.DAY_OF_WEEK]

        // Workaround cause DAY_OF_WEEK starts from Sunday
        if (dayOfWeek == Calendar.SUNDAY) dayOfWeek = 8
        cal.add(Calendar.DATE, -dayOfWeek + i + 2)
        holder.binding.date = cal.time

        if (itemList.size == 7) {

            val item = itemList[i]
            holder.binding.model = item

            // Create layout manager with initial prefetch item count
            val layoutManager = LinearLayoutManager(
                holder.binding.rvSubItem.context,
                LinearLayoutManager.VERTICAL,
                false
            )
            layoutManager.initialPrefetchItemCount = item.assignments.size

            // Create sub item view adapter
            if (holder.binding.rvSubItem.adapter is SubItemAdapter) {
                (holder.binding.rvSubItem.adapter as SubItemAdapter).setList(item.assignments)
            } else {
                val subItemAdapter = SubItemAdapter(item.assignments, cal.time, onClick)
                holder.binding.rvSubItem.adapter = subItemAdapter
            }
            holder.binding.rvSubItem.layoutManager = layoutManager
            holder.binding.rvSubItem.setRecycledViewPool(viewPool)
        }
    }

    override fun getItemCount(): Int = 7

    fun setList(list: List<WorkoutDate>) {
        itemList = list
        notifyDataSetChanged()
    }

    inner class ItemViewHolder(val binding: ItemBinding) : RecyclerView.ViewHolder(binding.root)
}