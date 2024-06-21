package com.example.mad.Interface

import com.example.mad.models.QuoteResponse
import com.example.mad.models.FeelingResponse
import com.example.mad.models.User
import com.example.mad.models.UserLogin
import okhttp3.RequestBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface RetrofitServices {

    @GET("quotes")
    suspend fun getQuotes(): Response<QuoteResponse>

    @GET("feelings")
    suspend fun getFeelings(): Response<FeelingResponse>

    @POST("user/login")
    suspend fun postUser(
        @Body requestBody: UserLogin
    ): Response<User>


}