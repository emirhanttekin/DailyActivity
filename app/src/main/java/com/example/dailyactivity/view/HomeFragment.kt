package com.example.dailyactivity.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dailyactivity.adapter.HomeAdapter
import com.example.dailyactivity.database.AppDatabase
import com.example.dailyactivity.databinding.FragmentHomeBinding
import com.example.dailyactivity.repository.TaskRepository
import com.example.dailyactivity.viewmodel.TaskViewModel
import com.example.dailyactivity.viewmodel.TaskViewModelFactory

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var adapter: HomeAdapter
    private val taskViewModel: TaskViewModel by viewModels {
        TaskViewModelFactory(TaskRepository(AppDatabase.getInstance(requireContext()).taskDao()))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        observeViewModel()
    }

    private fun setupRecyclerView() {
        adapter = HomeAdapter(emptyList())
        binding.homeRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.homeRecyclerView.adapter = adapter
    }

    private fun observeViewModel() {
        taskViewModel.tasks.observe(viewLifecycleOwner) { tasks ->
            adapter.updateTasks(tasks)
        }
    }
}
