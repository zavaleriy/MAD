package com.example.mad

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import androidx.navigation.NavHost
import androidx.navigation.Navigation
import com.example.mad.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var bottomNav: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.view_container) as NavHost
        val navController = navHostFragment.navController

        navController.navigate(R.id.splashScreen)

        navController.addOnDestinationChangedListener { _, dest, _ ->
            when (dest.id) {
                R.id.mainFragment,
                R.id.listenFragment,
                R.id.profileFragment -> {
                    binding.bottomNavigationView.isVisible = true
                }
                else -> {
                    binding.bottomNavigationView.isVisible = false
                }
            }
        }

        binding.bottomNavigationView.isVisible = false
        binding.bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.main_fragment -> {
                    navController.navigate(R.id.mainFragment)
                    return@setOnItemSelectedListener true
                }
                R.id.listen_fragment -> {
                    navController.navigate(R.id.listenFragment)
                    return@setOnItemSelectedListener true
                }
                R.id.profile_fragment -> {
                    navController.navigate(R.id.profileFragment)
                    return@setOnItemSelectedListener true
                }
                else -> false
            }
        }

        setContentView(binding.root)

    }

}