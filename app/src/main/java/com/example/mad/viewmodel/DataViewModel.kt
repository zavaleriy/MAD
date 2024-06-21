package com.example.navigation.Viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mad.models.Feeling
import com.example.mad.models.Quote
import com.example.mad.Interface.RetrofitServices
import com.example.mad.Retrofit.RetrofitBuilder
import com.example.mad.ui.LoginState
import kotlinx.coroutines.launch

class DataViewModel : ViewModel() {

    private val _feelingsData = MutableLiveData<List<Feeling>>()
    val feelingsData: LiveData<List<Feeling>> = _feelingsData

    private val _quotesData = MutableLiveData<List<Quote>>()
    val quotesData: LiveData<List<Quote>> = _quotesData

    private val apiService = RetrofitBuilder.retrofitService

    fun fetchData() {
        if (_feelingsData.value == null) {
            fetchFeelings()
        }
        if (_quotesData.value == null) {
            fetchQuotes()
        }
    }

    private fun fetchFeelings() {
        viewModelScope.launch {
            try {
                val response = apiService.getFeelings()
                if (response.isSuccessful) {
                    val feelings = response.body()?.data
                    feelings?.let {
                        _feelingsData.postValue(it)
                    }
                } else {
                    Log.e("GetFeeling", "Unsuccessful response: ${response.code()}")
                }
            } catch (e: Exception) {
                Log.e("GetFeeling", "Error: ${e.message}", e)
            }
        }
    }

    private fun fetchQuotes() {
        viewModelScope.launch {
            try {
                val response = apiService.getQuotes()
                if (response.isSuccessful) {
                    val quotes = response.body()?.data
                    quotes?.let {
                        _quotesData.postValue(it)
                    }
                } else {
                    Log.e("GetQuote", "Unsuccessful response: ${response.code()}")
                }
            } catch (e: Exception) {
                Log.e("GetQuote", "Error: ${e.message}", e)
            }
        }
    }
}
