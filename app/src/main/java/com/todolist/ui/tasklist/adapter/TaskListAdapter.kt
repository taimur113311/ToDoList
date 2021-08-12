package com.todolist.ui.tasklist.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.todolist.database.TaskEntity
import com.todolist.databinding.TaskItemListBinding
import com.todolist.utils.ClickListnerType
import com.todolist.utils.DateFormatter

class TaskListAdapter(
    private val data: List<TaskEntity>,
    var onClickItem: ((item: TaskEntity, status: String) -> Unit)? = null
) :
    RecyclerView.Adapter<TaskListAdapter.ViewHolder>() {
    private lateinit var context: Context


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = TaskItemListBinding.inflate(LayoutInflater.from(parent.context))
        context = parent.context
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position])
        val item = data[position]

        when (item.isCompleted) {
            true -> holder.binding.isCompletedCheckBox.isChecked = true
            else -> holder.binding.isCompletedCheckBox.isChecked = false
        }
        holder.binding.isCompletedCheckBox.setOnCheckedChangeListener { _, b ->
            onClickItem?.invoke(item, ClickListnerType.CHECKED.name)
        }
        holder.binding.btnEdit.setOnClickListener {
            onClickItem?.invoke(item, ClickListnerType.EDIT.name)
        }
        val currentDate = item.dateTime?.let { DateFormatter().dateTimeParse(it) }
        holder.binding.tvDate.text = "Date: " + currentDate?.first
        holder.binding.tvTime.text = "Time: " + currentDate?.second
    }


    inner class ViewHolder(val binding: TaskItemListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: TaskEntity) {
            binding.item = item

        }
    }
}