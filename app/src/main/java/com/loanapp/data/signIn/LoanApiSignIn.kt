package com.loanapp.data.signIn


import com.loanapp.data.UserInfo
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST

interface LoanApiSignIn {
    @Headers("Content-Type: application/json")
    @POST("login")
    fun signIn(@Body userData: UserInfo): Call<String>
}