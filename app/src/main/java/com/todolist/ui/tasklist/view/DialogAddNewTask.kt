package com.todolist.ui.tasklist.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.todolist.R
import com.todolist.database.TaskEntity
import com.todolist.databinding.DialogAddNewTaskBinding
import com.todolist.ui.tasklist.viewmodel.DialogAddTaskViewModel
import com.todolist.utils.EventObserver
import com.todolist.utils.UtilExtensions.myToast

class DialogAddNewTask : DialogFragment() {
    private var listId: Int? = null
    private var isEdit: Boolean? = null
    private var item: TaskEntity? = null

    companion object {
        private const val LIST_ID = "LIST_ID"
        private const val ISEDIT = "ISEDIT"
        private const val TaskEntity = "TaskEntity"
        fun newInstance(param1: Int?, b: Boolean, item: TaskEntity? = null): DialogAddNewTask {
            val args = Bundle()
            param1?.let { args.putInt(LIST_ID, it) }
            args.putBoolean(ISEDIT, b)
            item?.let { args.putParcelable(TaskEntity, it) }
            val fragment = DialogAddNewTask()
            fragment.arguments = args
            return fragment
        }

    }

    private val viewModel: DialogAddTaskViewModel by lazy {
        ViewModelProvider(
            this,
            DialogAddTaskViewModel.Factory(listId, isEdit, item)
        ).get(DialogAddTaskViewModel::class.java)
    }

    private lateinit var binding: DialogAddNewTaskBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.full_screen_dialog)
        if (arguments != null) {
            if (requireArguments().containsKey(LIST_ID)) {
                requireArguments().getInt(LIST_ID).let {
                    listId = it
                }
            }
            if (requireArguments().containsKey(ISEDIT)) {
                requireArguments().getBoolean(ISEDIT).let {
                    isEdit = it
                }
            }
            if (requireArguments().containsKey(TaskEntity)) {
                item = requireArguments().getParcelable(TaskEntity)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DialogAddNewTaskBinding.inflate(inflater)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        dialog?.setCancelable(false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observerVariables()
    }

    private fun observerVariables() {

        viewModel.dismissDialog.observe(viewLifecycleOwner, EventObserver {
            dismiss()
        })

        viewModel.callAPI.observe(viewLifecycleOwner, EventObserver {
            context?.let { it1 ->
                if (isEdit == true) {
                    viewModel.updateTask()
                    it1.myToast("Task Successfully Updated!")
                } else {
                    viewModel.addNewList()
                    it1.myToast("Task Successfully Added to List!")
                }
            }
        })
    }

}