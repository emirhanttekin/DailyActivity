package com.example.dailyactivity.view

import android.content.Context
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
import com.example.dailyactivity.utils.SharedPreferencesHelper
import com.example.dailyactivity.viewmodel.TaskViewModel
import com.example.dailyactivity.viewmodel.TaskViewModelFactory

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var adapterToday: HomeAdapter // Sadece adapterToday kullanılacak
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

        val userId = SharedPreferencesHelper.getUserId(requireContext())

        if (userId != -1) {
            setupRecyclerView()
            observeViewModel(userId)
        } else {
            // Handle the case where userId is not found
        }

    }

    private fun setupRecyclerView() {
        adapterToday = HomeAdapter(emptyList(), HomeAdapter.VIEW_TYPE_NORMAL)
        binding.homeRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.homeRecyclerView.adapter = adapterToday
    }

    private fun observeViewModel(userId: Int) {
        taskViewModel.setUserId(userId)
        taskViewModel.allTasks.observe(viewLifecycleOwner) { tasks ->
            val todayTasks = tasks.filter { it.isToday() } // Bugünün görevlerini filtrele
            adapterToday.updateTasks(todayTasks) // Sadece adapterToday'i güncelle
            // Kullanıcının ismini TextView'e yerleştirme
            val userName = SharedPreferencesHelper.getUserName(requireContext())
            binding.nameTxt.text = "Merhaba, $userName"
        }
    }
}
