package com.example.dailyactivity.view

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dailyactivity.R
import com.example.dailyactivity.adapter.HomeAdapter
import com.example.dailyactivity.database.AppDatabase
import com.example.dailyactivity.databinding.FragmentTaskBinding
import com.example.dailyactivity.repository.TaskRepository
import com.example.dailyactivity.utils.HorizontalSpaceItemDecoration
import com.example.dailyactivity.utils.SharedPreferencesHelper
import com.example.dailyactivity.viewmodel.TaskViewModel
import com.example.dailyactivity.viewmodel.TaskViewModelFactory

class TaskFragment : Fragment() {
    private lateinit var binding: FragmentTaskBinding
    private lateinit var adapterAll: HomeAdapter
    private lateinit var adapterToday: HomeAdapter
    private lateinit var adapterThisWeek: HomeAdapter
    private val taskViewModel: TaskViewModel by viewModels {
        TaskViewModelFactory(TaskRepository(AppDatabase.getInstance(requireContext()).taskDao()))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTaskBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerViewAndObserveViewModel()
    }

    private fun setupRecyclerViewAndObserveViewModel() {
        adapterAll = HomeAdapter(emptyList(), HomeAdapter.VIEW_TYPE_ALTERNATE)
        adapterToday = HomeAdapter(emptyList(), HomeAdapter.VIEW_TYPE_ALTERNATE)
        adapterThisWeek = HomeAdapter(emptyList(), HomeAdapter.VIEW_TYPE_ALTERNATE)

        val padding = resources.getDimensionPixelSize(R.dimen.horizontal_padding)

        setupRecyclerView(binding.homeAllrecyclerView3, adapterAll, padding)
        setupRecyclerView(binding.homeTodayrecyclerView, adapterToday, padding)
        setupRecyclerView(binding.homeThisweekrecyclerView2, adapterThisWeek, padding)

        observeTasks()
    }

    private fun observeTasks() {
        val userId = SharedPreferencesHelper.getUserId(requireContext())
        Log.d("TaskFragment", "User ID: $userId")

        taskViewModel.setUserId(userId)

        taskViewModel.getTasksForToday(userId).observe(viewLifecycleOwner, { tasks ->
            adapterToday.updateTasks(tasks)
        })

        taskViewModel.getTasksForThisWeek(userId).observe(viewLifecycleOwner, { tasks ->
            adapterThisWeek.updateTasks(tasks)
        })

        taskViewModel.getTasksForAll(userId).observe(viewLifecycleOwner, { tasks ->
            adapterAll.updateTasks(tasks)
        })
    }

    private fun setupRecyclerView(recyclerView: RecyclerView, adapter: HomeAdapter, padding: Int) {
        recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        recyclerView.adapter = adapter
        recyclerView.addItemDecoration(HorizontalSpaceItemDecoration(padding))
    }
}
