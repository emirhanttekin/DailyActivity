package com.example.dailyactivity

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.dailyactivity.R
import com.example.dailyactivity.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set the action bar
        setSupportActionBar(binding.toolbar)

        // Hide the toolbar
        supportActionBar?.hide()

        // NavController
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        navController = navHostFragment.navController

        // Fragment changes listener
        navController.addOnDestinationChangedListener { _, destination, _ ->
            handleBottomMenuVisibility(destination.id)
            hideKeyboard()
        }

        setupNavigation()
    }

    private fun handleBottomMenuVisibility(destinationId: Int) {
        val bottomMenu = findViewById<View>(R.id.custom_bottom_menu)

        bottomMenu.visibility = when (destinationId) {
            R.id.homeFragment, R.id.taskFragment -> View.VISIBLE
            else -> View.GONE
        }
    }

    fun onMenuItemClicked(view: View) {
        val iconHome = findViewById<ImageView>(R.id.icon_home)
        val iconGroup678 = findViewById<ImageView>(R.id.icon_group678)
        val iconFolder = findViewById<ImageView>(R.id.icon_folder)
        val activity = findViewById<ImageView>(R.id.graphich)
        val btnEkle = findViewById<ImageView>(R.id.btn_ekle)


        when (view.id) {
            R.id.icon_home -> {
                iconHome.setBackgroundResource(R.drawable.homeblue)
                iconGroup678.setBackgroundResource(R.drawable.group679)
                iconFolder.setBackgroundResource(R.drawable.folder)
                activity.setBackgroundResource(R.drawable.activity)
                btnEkle.setBackgroundResource(R.drawable.group680)

                navController.navigate(R.id.homeFragment)
            }
            R.id.icon_group678 -> {
                iconHome.setBackgroundResource(R.drawable.home)
                iconGroup678.setBackgroundResource(R.drawable.group679blue)
                iconFolder.setBackgroundResource(R.drawable.folder)
                activity.setBackgroundResource(R.drawable.activity)
                btnEkle.setBackgroundResource(R.drawable.group680)

                navController.navigate(R.id.taskFragment)
            }
            R.id.icon_folder -> {
                iconHome.setBackgroundResource(R.drawable.home)
                iconGroup678.setBackgroundResource(R.drawable.group679)
                iconFolder.setBackgroundResource(R.drawable.folderblue)
                activity.setBackgroundResource(R.drawable.activity)
                btnEkle.setBackgroundResource(R.drawable.group680)

                navController.navigate(R.id.profileFragment)
            }
            R.id.graphich -> {
                iconHome.setBackgroundResource(R.drawable.home)
                iconGroup678.setBackgroundResource(R.drawable.group679)
                iconFolder.setBackgroundResource(R.drawable.folder)
                activity.setBackgroundResource(R.drawable.activityblue)
                btnEkle.setBackgroundResource(R.drawable.group680)

                navController.navigate(R.id.graphichFragment) // Navigate to GraphichFragment
            }
            R.id.btn_ekle -> {
                iconHome.setBackgroundResource(R.drawable.home)
                iconGroup678.setBackgroundResource(R.drawable.group679)
                iconFolder.setBackgroundResource(R.drawable.folder)
                activity.setBackgroundResource(R.drawable.activity)
                btnEkle.setBackgroundResource(R.drawable.group680)

                navController.navigate(R.id.addTaskFragment) // Navigate to AddTaskFragment
            }
        }
    }

    private fun setupNavigation() {
        val appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    private fun hideKeyboard() {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
    }
}
