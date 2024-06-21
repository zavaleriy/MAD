package com.example.mad

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import androidx.navigation.fragment.findNavController
import com.example.mad.databinding.FragmentOnBoardingBinding

class onBoarding : Fragment(R.layout.fragment_on_boarding) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var binding = FragmentOnBoardingBinding.bind(view)
        binding.ButtonLogin.setOnClickListener {
            findNavController().navigate(R.id.action_onBoarding_to_loginFragment)
        }

        binding.ButtonReg.setOnClickListener {
            findNavController().navigate(R.id.action_onBoarding_to_regFragment)
        }

    }


}