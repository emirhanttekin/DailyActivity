package com.example.dailyactivity.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.navigation.fragment.findNavController
import com.example.dailyactivity.R
import com.example.dailyactivity.databinding.FragmentFirstSpalshBinding
import com.example.dailyactivity.databinding.FragmentSpalshBinding


class FirstSpalshFragment : Fragment() {
    private lateinit var binding  : FragmentFirstSpalshBinding



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentFirstSpalshBinding.inflate(inflater , container , false)
        return  binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val animation = AnimationUtils.loadAnimation(requireContext(), R.anim.scale_animation)

        // AnimationListener kullanarak animasyonun bitişini dinle
        animation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation?) {
                // Animasyon başladığında yapılacak işlemler
            }

            override fun onAnimationEnd(animation: Animation?) {
                // Animasyon bittiğinde yapılacak işlemler
                findNavController().navigate(R.id.action_firstSpalshFragment_to_spalshFragment)
            }

            override fun onAnimationRepeat(animation: Animation?) {
                // Animasyon tekrarlandığında yapılacak işlemler
            }
        })

        // Animasyonu başlat
        binding.logo.startAnimation(animation)
    }
}

