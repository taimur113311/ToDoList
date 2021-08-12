package com.todolist.ui.completedlist.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.todolist.database.PendingCompletedList
import com.todolist.databinding.FragmentCompletedListBinding
import com.todolist.ui.MainActivity
import com.todolist.ui.addnewlist.view.AddEditListDialog
import com.todolist.ui.completedlist.viewmodel.CompletedListViewModel
import com.todolist.ui.pendinglist.adapter.ToDOListItemAdapter
import com.todolist.ui.tasklist.view.TaskListFragment
import com.todolist.utils.ClickListnerType
import com.todolist.utils.SwipeToDeleteCallback
import com.todolist.utils.UtilExtensions.isVisible
import com.todolist.utils.UtilExtensions.myToast

class CompletedListFragment : Fragment() {
    private val viewModel: CompletedListViewModel by lazy {
        ViewModelProvider(
            this,
            CompletedListViewModel.Factory()
        ).get(CompletedListViewModel::class.java)
    }

    private lateinit var binding: FragmentCompletedListBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentCompletedListBinding.inflate(inflater)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.fetchData()
        observerVariables()
        deleteTaskItem()
    }

    private fun observerVariables() {
        viewModel.completedToDoList.observe(viewLifecycleOwner, Observer {

            if (it.isEmpty().not()) {
                binding.emptyTaskView.isVisible(false)
                val adapter = ToDOListItemAdapter(it) { item, type ->
                    when (type) {
                        ClickListnerType.DETAIL.name -> goToTaskScreen(item)
                        ClickListnerType.EDIT.name -> {
                            editList(item)
                        }
                    }
                }
                binding.completedListRecylerview.adapter = adapter
            } else {
                binding.emptyTaskView.isVisible(true)
            }
        })
    }

    private fun editList(item: PendingCompletedList) {
        AddEditListDialog.newInstance(true, item).show(childFragmentManager, "AddEditListDialog")

    }

    private fun deleteTaskItem() {
        context?.let {
            val swipeHandler = object : SwipeToDeleteCallback(it) {
                override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder
                ): Boolean {
                    TODO("Not yet implemented")
                }

                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    deleteDataFromDb(viewHolder.adapterPosition)
                }
            }
            val itemTouchHelper =
                ItemTouchHelper(swipeHandler)
            itemTouchHelper.attachToRecyclerView(binding.completedListRecylerview)
        }
    }

    private fun deleteDataFromDb(adapterPosition: Int) {
        viewModel.deleteDataFromDB(viewModel.completedList[adapterPosition].listId)
        context?.myToast("List Successfully Removed!")
    }

    private fun goToTaskScreen(item: PendingCompletedList) {
        // load fragment
        (activity as MainActivity).loadFragment(TaskListFragment.newInstance(item), true)
    }
}