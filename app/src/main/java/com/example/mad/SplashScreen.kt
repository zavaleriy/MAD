package com.example.mad

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.mad.databinding.FragmentSplashScreenBinding
import com.example.mad.managers.UserManager
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class SplashScreen : Fragment(R.layout.fragment_splash_screen) {

    private lateinit var userManager: UserManager

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var fragmentSplashScreenBinding = FragmentSplashScreenBinding.bind(view)
        userManager = UserManager(requireContext())

        runBlocking {

        }
        fragmentSplashScreenBinding.SplashButton.setOnClickListener {
            lifecycleScope.launch {
                if (userManager.hasData())
                {
                    findNavController().navigate(R.id.action_splashScreen_to_mainFragment)
                } else {
                    findNavController().navigate(R.id.action_splashScreen_to_onBoarding)
                }
            }



        }

    }

}