package com.example.mad

import android.net.http.HttpException
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.datastore.core.DataStore
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import androidx.fragment.app.viewModels
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import com.example.mad.Retrofit.RetrofitBuilder
import com.example.mad.databinding.FragmentLoginBinding
import com.example.mad.managers.UserManager
import com.example.mad.models.Feeling
import com.example.mad.models.UserLogin
import com.example.mad.ui.LoginState
import com.example.navigation.Viewmodel.LoginViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.prefs.Preferences

class LoginFragment : Fragment(R.layout.fragment_login) {

    private lateinit var binding: FragmentLoginBinding
    private lateinit var userManager: UserManager
    private val loginViewModel: LoginViewModel by viewModels()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentLoginBinding.bind(view)
        userManager = UserManager(requireContext())

        val test: LoginState = LoginState.Load
//        when(test){
//            is LoginState.Load -> {
//
//            }
//            is LoginState.ValidationError -> {
//
//            }
//        }

        val email = binding.EditEmail
        val password = binding.EditPassword

        lifecycleScope.launch {
            var email =
                if (userManager.userEmailFlow.first().isEmpty()) ""
                else userManager.userEmailFlow.first().toString()
            
            binding.EditEmail.setText(email)
        }

        binding.ButtonSignIn.setOnClickListener{
            loginViewModel.loginUser(email.text.toString(), password.text.toString()) {logged ->
                if (logged)
                    findNavController().navigate(R.id.action_loginFragment_to_mainFragment)
            }
        }

        binding.ButtonRegister.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_regFragment)
        }

    }

}