package com.example.dailyactivity.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.dailyactivity.R
import com.example.dailyactivity.databinding.FragmentLoginBinding


class LoginFragment : Fragment() {

    private lateinit var  binding : FragmentLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var binding = FragmentLoginBinding.inflate(inflater , container ,false)
        // Inflate the layout for this fragment
        return binding.root
    }


}