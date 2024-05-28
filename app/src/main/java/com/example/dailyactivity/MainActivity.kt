package com.example.dailyactivity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.navigation.fragment.NavHostFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // NavController'ı al
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        val navController = navHostFragment.navController

        // Fragment değişikliklerini izleyin
        navController.addOnDestinationChangedListener { _, destination, _ ->
            handleBottomMenuVisibility(destination.id)

            val btnEkle = findViewById<ImageView>(R.id.btn_ekle)
            btnEkle.setOnClickListener {
                navController.navigate(R.id.action_homeFragment_to_addTaskFragment)
            }
        }
    }

    private fun handleBottomMenuVisibility(destinationId: Int) {
        val bottomMenu = findViewById<View>(R.id.custom_bottom_menu)

        bottomMenu.visibility = if (destinationId == R.id.homeFragment) {
            View.VISIBLE
        } else {
            View.GONE
        }
    }
}
