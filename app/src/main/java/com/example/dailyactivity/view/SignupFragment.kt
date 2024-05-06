package com.example.dailyactivity.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.dailyactivity.R
import com.example.dailyactivity.database.AppDatabase
import com.example.dailyactivity.databinding.FragmentSignupBinding
import com.example.dailyactivity.entity.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class SignupFragment : Fragment() {
    private lateinit var  binding : FragmentSignupBinding
    private lateinit var db : AppDatabase



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentSignupBinding.inflate(inflater, container, false)

        return  binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        db = AppDatabase.getInstance(requireContext())
        binding.imageView14.setOnClickListener {
            val username =  binding.editUsername.text.toString()
            val password = binding.signupPassword.text.toString()
            val  email = binding.signupEmail.text.toString()
            val phone = binding.editPhone.text.toString()

            val newUser =  User(username = username , password = password , email = email, phone = phone)
            GlobalScope.launch(Dispatchers.IO) {
                db.userDao().insert(newUser)
            }
            findNavController().navigate(R.id.action_signupFragment_to_loginFragment)
        }




    }


}