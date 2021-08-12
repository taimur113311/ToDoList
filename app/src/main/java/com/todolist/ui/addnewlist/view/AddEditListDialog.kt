package com.todolist.ui.addnewlist.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.todolist.R
import com.todolist.database.PendingCompletedList
import com.todolist.databinding.DialogAddNewListBinding
import com.todolist.ui.addnewlist.viewmodel.AddEditListDialogViewModel
import com.todolist.utils.EventObserver
import com.todolist.utils.UtilExtensions.myToast

class AddEditListDialog : DialogFragment() {
    private var isEdit: Boolean? = null
    private var item: PendingCompletedList? = null

    companion object {
        private const val ISEDIT = "ISEDIT"
        private const val LISTITEM = "LISTITEM"
        fun newInstance(
            isEdit: Boolean? = null,
            item: PendingCompletedList? = null
        ): AddEditListDialog {
            val args = Bundle()
            isEdit?.let { args.putBoolean(ISEDIT, it) }
            item?.let { args.putParcelable(LISTITEM, it) }

            val fragment = AddEditListDialog()
            fragment.arguments = args
            return fragment
        }

    }

    private val dialogViewModel: AddEditListDialogViewModel by lazy {
        ViewModelProvider(
            this,
            AddEditListDialogViewModel.Factory(isEdit, item)
        ).get(AddEditListDialogViewModel::class.java)
    }

    private lateinit var binding: DialogAddNewListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.full_screen_dialog)
        if (arguments != null && (requireArguments().containsKey(ISEDIT) || requireArguments().containsKey(
                LISTITEM))) {
            requireArguments().getBoolean(ISEDIT).let {
                isEdit = it
            }
            item = requireArguments().getParcelable(LISTITEM)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DialogAddNewListBinding.inflate(inflater)
        binding.viewModel = dialogViewModel
        binding.lifecycleOwner = viewLifecycleOwner
        dialog?.setCancelable(false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observerVariables()
    }

    private fun observerVariables() {

        dialogViewModel.dismissDialog.observe(viewLifecycleOwner, EventObserver {
            dismiss()
        })
        dialogViewModel.callAPI.observe(viewLifecycleOwner, EventObserver {
            context?.let { it1 ->
                if (isEdit == true) {
                    dialogViewModel.updateList(item)
                    context?.myToast("List Successfully Updated!")
                } else {
                    dialogViewModel.addNewList()
                    it1.myToast("New List Successfully Saved")
                }
            }
        })
    }

}