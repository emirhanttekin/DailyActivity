package com.example.dailyactivity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.navigation.findNavController
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

        val iconHome = findViewById<ImageView>(R.id.icon_home)
        iconHome.setBackgroundResource(R.drawable.homeblue)
    }

    private fun handleBottomMenuVisibility(destinationId: Int) {
        val bottomMenu = findViewById<View>(R.id.custom_bottom_menu)

        bottomMenu.visibility = when(destinationId) {
            R.id.homeFragment, R.id.taskFragment ->View.VISIBLE

            else  -> View.GONE

        }


    }
    fun onMenuItemClicked(view: View) {
        val iconHome = findViewById<ImageView>(R.id.icon_home)
        val iconGroup678 = findViewById<ImageView>(R.id.icon_group678)
        val iconFolder = findViewById<ImageView>(R.id.icon_folder)
        val activity = findViewById<ImageView>(R.id.activity)

        when (view.id) {
            R.id.icon_home -> {
                iconHome.setBackgroundResource(R.drawable.homeblue)
                iconGroup678.setBackgroundResource(R.drawable.group679)
                iconFolder.setBackgroundResource(R.drawable.folder)
                activity.setBackgroundResource(R.drawable.activity)

                val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
                val navController = navHostFragment.navController
                navController.navigate(R.id.homeFragment)
            }
            R.id.icon_group678 -> {
                iconHome.setBackgroundResource(R.drawable.home)
                iconGroup678.setBackgroundResource(R.drawable.group679blue)
                iconFolder.setBackgroundResource(R.drawable.folder)
                activity.setBackgroundResource(R.drawable.activity)

                val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
                val navController = navHostFragment.navController
                navController.navigate(R.id.taskFragment)

            }

            R.id.icon_folder -> {
                iconHome.setBackgroundResource(R.drawable.home)
                iconGroup678.setBackgroundResource(R.drawable.group679)
                iconFolder.setBackgroundResource(R.drawable.folderblue)
                activity.setBackgroundResource(R.drawable.activity)
            }
            R.id.activity -> {
                iconHome.setBackgroundResource(R.drawable.home)
                iconGroup678.setBackgroundResource(R.drawable.group679)
                iconFolder.setBackgroundResource(R.drawable.folder)
                activity.setBackgroundResource(R.drawable.activityblue)
            }
        }
    }

}
