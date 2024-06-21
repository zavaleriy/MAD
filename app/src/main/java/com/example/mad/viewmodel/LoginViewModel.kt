package com.example.navigation.Viewmodel


import android.app.Application
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.mad.MainActivity
import com.example.mad.models.UserLogin
import com.example.mad.Retrofit.RetrofitBuilder
import com.example.mad.managers.UserManager
import kotlinx.coroutines.launch


class LoginViewModel(private val application: Application) : AndroidViewModel(application) {

    private val userManager: UserManager =
        UserManager(application.baseContext)

    fun loginUser(email: String, password: String, callback: (Boolean) -> Unit) {
        val apiService = RetrofitBuilder.retrofitService
        val loginRequest = UserLogin(email, password)

        viewModelScope.launch {
            val result = apiService.postUser(loginRequest)
            result.let { response ->
                if (response.isSuccessful) {
                    Log.d("PostLogin_RESPONSE", response.body().toString())
                    userManager.storeUser(
                        response.body()?.email!!,
                        response.body()?.nickName!!,
                        response.body()?.avatar!!,
                        response.body()?.token!!
                    )
                    Toast.makeText(application.applicationContext, "Вход выполнен успешно", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(application.applicationContext, "Error", Toast.LENGTH_SHORT).show()
                }
                callback(response.isSuccessful)
            }
        }
    }
}
