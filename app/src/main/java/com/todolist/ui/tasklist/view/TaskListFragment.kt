package com.todolist.ui.tasklist.view

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
import com.todolist.database.TaskEntity
import com.todolist.databinding.FragmentTaskListBinding
import com.todolist.ui.MainActivity
import com.todolist.ui.tasklist.adapter.TaskListAdapter
import com.todolist.ui.tasklist.viewmodel.TaskListViewModel
import com.todolist.utils.ClickListnerType
import com.todolist.utils.SwipeToDeleteCallback
import com.todolist.utils.UtilExtensions.isVisible
import com.todolist.utils.UtilExtensions.myToast

private const val PendingCompletedList = "PendingCompletedList"

class TaskListFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var pendingCompletedList: PendingCompletedList? = null
    private val viewModel: TaskListViewModel by lazy {
        ViewModelProvider(
            this,
            TaskListViewModel.Factory(pendingCompletedList)
        ).get(TaskListViewModel::class.java)
    }

    private lateinit var binding: FragmentTaskListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null && requireArguments().containsKey(PendingCompletedList)) {
            pendingCompletedList = requireArguments().getParcelable(PendingCompletedList)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentTaskListBinding.inflate(inflater)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getTaskItemsOfList()

        (activity as MainActivity).title = pendingCompletedList?.title
        observerVariables()
        clickListener()
        deleteTaskItem()
    }

    private fun clickListener() {
        binding.btnContinue.setOnClickListener {
            DialogAddNewTask.newInstance(pendingCompletedList?.listId!!, false, null)
                .show(childFragmentManager, "DialogAddNewTask")

        }
    }

    private fun observerVariables() {
        viewModel.taskItemsOfList.observe(viewLifecycleOwner, Observer {
            if (it.isEmpty().not()) {
                viewModel.taskItems = it
                binding.emptyTaskView.isVisible(false)
                binding.taskItemListRecylerview.isVisible(true)
                binding.taskItemListRecylerview.adapter = TaskListAdapter(it) { item, status ->
                    when (status) {
                        ClickListnerType.EDIT.name -> {
                            editTaskItem(item)
                        }
                        ClickListnerType.CHECKED.name -> {
                            updateTaskData(item)
                        }
                    }
                }
            } else {
                binding.taskItemListRecylerview.isVisible(false)
                binding.emptyTaskView.isVisible(true)
            }
        })
    }

    private fun editTaskItem(item: TaskEntity) {
        DialogAddNewTask.newInstance(item.id_fk_List, true, item)
            .show(childFragmentManager, "DialogAddNewTask")

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
            itemTouchHelper.attachToRecyclerView(binding.taskItemListRecylerview)
        }
    }

    private fun deleteDataFromDb(adapterPosition: Int) {
        viewModel.deleteDataFromDB(viewModel.taskItems[adapterPosition].taskId)
        context?.myToast("Task Successfully Removed!")
    }


    private fun updateTaskData(item: TaskEntity) {
        when (item.isCompleted) {
            true -> viewModel.updateTaskData(item.taskId, false)
            else -> viewModel.updateTaskData(item.taskId, true)
        }
        context?.myToast("Status Updated!")

    }


    companion object {
        @JvmStatic
        fun newInstance(param1: PendingCompletedList) =
            TaskListFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(PendingCompletedList, param1)
                }
            }
    }
}