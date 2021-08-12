package com.todolist.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.todolist.R
import com.todolist.databinding.ActivityMainBinding
import com.todolist.ui.addnewlist.view.AddEditListDialog
import com.todolist.ui.completedlist.view.CompletedListFragment
import com.todolist.ui.pendinglist.view.PendingListFragment


class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var fragmentManager: FragmentManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =
            DataBindingUtil.setContentView(this, R.layout.activity_main)
        fragmentManager = supportFragmentManager
        bottomNavigationClick()
        setOnClickListener()
    }

    private fun bottomNavigationClick() {
        binding.bottomNavigation.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home -> {
                    title = getString(R.string.pending_list)
                    loadFragment(PendingListFragment(), false)
                    true
                }
                else -> {
                    loadFragment(CompletedListFragment(), false)
                    title = getString(R.string.completed_list)
                    true
                }
            }

        }
        binding.bottomNavigation.selectedItemId = R.id.home
    }

    private fun setOnClickListener() {
        binding.fab.setOnClickListener {
            AddEditListDialog.newInstance(false).show(fragmentManager, "DialogAddNewList")
        }
    }

    //Helper method to load fragment
    public fun loadFragment(
        fragment: Fragment,
        addToBackStack: Boolean
    ) {
        // load fragment
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.framgeLayout, fragment)
        if (addToBackStack) {
            transaction.addToBackStack(null)
        }
        transaction.commit()
    }
}