package com.todolist.ui.pendinglist.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.todolist.database.PendingCompletedList
import com.todolist.databinding.ItemListBinding
import com.todolist.utils.ClickListnerType

class ToDOListItemAdapter(
    private var data: MutableList<PendingCompletedList>,
    var onClickItem: ((item: PendingCompletedList, type: String) -> Unit)? = null
) :
    RecyclerView.Adapter<ToDOListItemAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemListBinding.inflate(LayoutInflater.from(parent.context))
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(data[position])

    inner class ViewHolder(val binding: ItemListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: PendingCompletedList) {
            binding.item = item
            itemView.setOnClickListener {
                onClickItem?.invoke(item, ClickListnerType.DETAIL.name)
            }
            binding.btnEdit.setOnClickListener {
                onClickItem?.invoke(
                    item,
                    ClickListnerType.EDIT.name
                )
            }
        }
    }
}