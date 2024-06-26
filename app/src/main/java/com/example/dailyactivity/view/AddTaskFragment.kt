package com.example.dailyactivity.view

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.dailyactivity.R
import com.example.dailyactivity.database.AppDatabase
import com.example.dailyactivity.databinding.FragmentAddTaskBinding
import com.example.dailyactivity.entity.Task
import com.example.dailyactivity.repository.TaskRepository
import com.example.dailyactivity.utils.SharedPreferencesHelper
import com.example.dailyactivity.viewmodel.TaskViewModel
import com.example.dailyactivity.viewmodel.TaskViewModelFactory
import java.util.*

class AddTaskFragment : Fragment() {

    private lateinit var binding: FragmentAddTaskBinding
    private lateinit var viewModel: TaskViewModel
    private var userId: Int = -1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddTaskBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val database = AppDatabase.getInstance(requireContext())
        val taskDao = database.taskDao()
        val repository = TaskRepository(taskDao)
        val factory = TaskViewModelFactory(repository)
        viewModel = ViewModelProvider(this, factory).get(TaskViewModel::class.java)

        // Get userId from SharedPreferences
        userId = SharedPreferencesHelper.getUserId(requireContext())
        Log.d("AddTaskFragment", "User ID: $userId")

        setupRadioListeners()
        setupCalendarClickListener()

        binding.imgTaskbtn.setOnClickListener {
            val title = binding.baslikText.text.toString()
            val date = binding.editTextDate.text.toString()
            val startTime = binding.startTime.text.toString()
            val endTime = binding.endTime.text.toString()
            val description = binding.descriptionText.text.toString()
            val type = if (binding.type1.isChecked) "Personel" else if (binding.type2.isChecked) "Özel" else "Gizli"
            var tags = ""

            when {
                binding.tags1.isChecked -> tags = "Ofis"
                binding.tags2.isChecked -> tags = "Ev"
                binding.tags3.isChecked -> tags = "Acil"
                binding.tags4.isChecked -> tags = "İş"
            }

            if (userId != -1) {
                val task = Task(
                    userId = userId,
                    title = title,
                    startDate = date,
                    startTime = startTime,
                    endTime = endTime,
                    description = description,
                    type = type,
                    tags = tags
                )
                Log.d("AddTaskFragment", "Inserting task: $task")
                viewModel.insertTask(task)
            } else {
                Log.e("AddTaskFragment", "User ID is -1, cannot insert task.")
            }
            showSuccessDialog()
        }

        // Open calendar when EditText is clicked
        binding.editTextDate.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                openCalendar()
            }
        }
    }

    private fun setupRadioListeners() {
        binding.tags1.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                binding.tags2.isChecked = false
                binding.tags3.isChecked = false
                binding.tags4.isChecked = false
            }
        }
        binding.tags2.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                binding.tags1.isChecked = false
                binding.tags3.isChecked = false
                binding.tags4.isChecked = false
            }
        }
        binding.tags3.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                binding.tags1.isChecked = false
                binding.tags2.isChecked = false
                binding.tags4.isChecked = false
            }
        }
        binding.tags4.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                binding.tags1.isChecked = false
                binding.tags2.isChecked = false
                binding.tags3.isChecked = false
            }
        }
    }

    private fun setupCalendarClickListener() {
        binding.calendarImage.setOnClickListener {
            openCalendar()
        }
    }


    private fun openCalendar() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(requireContext(), { _, year, monthOfYear, dayOfMonth ->
            val selectedDate = "$dayOfMonth/${monthOfYear + 1}/$year"
            binding.editTextDate.setText(selectedDate)
        }, year, month, day)
        datePickerDialog.show()
    }

    private fun showSuccessDialog() {
        AlertDialog.Builder(requireContext())
            .setTitle("Başarılı")
            .setMessage("Görev Başarıyla Kaydedildi")
            .setPositiveButton("Tamam") { dialog, _ -> dialog.dismiss() }
            .show()
        findNavController().navigate(R.id.action_addTaskFragment_to_homeFragment)
    }
}
