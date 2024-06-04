package com.example.dailyactivity.extension

import android.content.Context
import androidx.fragment.app.Fragment

fun Fragment.setupRecyclerViewAndObserveViewModel(userId: Int, setupRecyclerView: () -> Unit, observeViewModel: (userId: Int) -> Unit) {
    val sharedPreferences = requireActivity().getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
    val savedUserId = sharedPreferences.getInt("userId", -1)

    if (savedUserId != -1) {
        setupRecyclerView()
        observeViewModel(savedUserId)
    } else {
        // Handle the case where userId is not found
        // For now, let's just call setupRecyclerView and observeViewModel with a default userId
        setupRecyclerView()
        observeViewModel(userId)
    }
}
