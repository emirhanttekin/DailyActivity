package com.example.dailyactivity.view

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.dailyactivity.R
import com.example.dailyactivity.database.AppDatabase
import com.example.dailyactivity.databinding.FragmentLoginBinding
import com.example.dailyactivity.repository.UserRepository
import com.example.dailyactivity.viewmodel.LoginViewModel
import com.example.dailyactivity.viewmodel.LoginViewModelFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding
    private lateinit var db: AppDatabase
    private val  loginViewModel : LoginViewModel by viewModels {
        LoginViewModelFactory(UserRepository(AppDatabase.getInstance(requireContext())))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        db = AppDatabase.getInstance(requireContext())

        binding.btnSignup.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_signupFragment)
        }

        binding.btnSigin.setOnClickListener {
            val email = binding.emailText.text.toString()
            val password = binding.passwordText.text.toString()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(
                    requireContext(),
                    "Email and Password cannot be empty",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }

            loginViewModel.login(email, password) { result ->
                when(result) {
                    is LoginViewModel.Result.Success -> {
                        val sharedPreferences = requireActivity().getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
                        sharedPreferences.edit().putInt("userId", result.user.id).apply()

                        Toast.makeText(requireContext(), "Giriş Başarılı", Toast.LENGTH_SHORT).show()
                        findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
                    }
                    is LoginViewModel.Result.Error -> {
                        Toast.makeText(requireContext(), result.message, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

    }
}


