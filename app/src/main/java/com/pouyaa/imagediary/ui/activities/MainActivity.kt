package com.pouyaa.imagediary.ui.activities

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.pouyaa.imagediary.R
import com.pouyaa.imagediary.databinding.ActivityMainBinding
import timber.log.Timber

class MainActivity : AppCompatActivity() {

    companion object {
        const val TAG = "MainActivity"
    }

    private lateinit var binding: ActivityMainBinding
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        drawerLayout = binding.drawerLayout
        navController = findNavController(R.id.nav_host_fragment)

        NavigationUI.setupActionBarWithNavController(this, navController, drawerLayout)

        appBarConfiguration = AppBarConfiguration(navController.graph, drawerLayout)
        NavigationUI.setupWithNavController(binding.navView, navController)

        navController.addOnDestinationChangedListener { controller, destination, _ ->
            if (destination.id == controller.graph.startDestination) {
                drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
            } else {
                drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
            }
        }

        Timber.i("onCreate")
    }

    override fun onStart() {
        super.onStart()
        Timber.i("onStart")
    }

    override fun onResume() {
        super.onResume()
        Timber.i("onResume")
    }

    override fun onPause() {
        super.onPause()
        Timber.i("onPause")
    }

    override fun onStop() {
        super.onStop()
        Timber.i("onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Timber.i("onDestroy")
    }

    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(navController, drawerLayout)
    }

    override fun onRestart() {
        Timber.i("onRestart()")
        super.onRestart()
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        Timber.i("onRestoreInstanceState()")
        super.onRestoreInstanceState(savedInstanceState)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        Timber.i("onSaveInstanceState()")
        super.onSaveInstanceState(outState)
    }
}
