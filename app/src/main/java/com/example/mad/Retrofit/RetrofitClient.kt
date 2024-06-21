package com.example.mad.Retrofit

import android.util.Log
import com.example.mad.Interface.RetrofitServices
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val startTime = System.currentTimeMillis()
        val response = chain.proceed(request)
        val endTime = System.currentTimeMillis()

        val requestLog = "Request: ${request.method()} ${request.url()}"
        val responseLog = "Response: ${response.code()} ${response.message()}"

        Log.d("Retrofit", requestLog)
        Log.d("Retrofit", responseLog)
        Log.d("Retrofit", "Time taken: ${endTime - startTime} ms")

        return response
    }
}

object RetrofitBuilder {

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("http://mskko2021.mad.hakta.pro/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    val retrofitService: RetrofitServices = getRetrofit().create(RetrofitServices::class.java)

}