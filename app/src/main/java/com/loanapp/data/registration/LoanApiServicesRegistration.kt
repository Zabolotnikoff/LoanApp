package com.loanapp.data.registration

import com.loanapp.data.UserInfo
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface LoanApiServicesRegistration {
    @Headers("Content-Type: application/json")
    @POST("registration")
    fun createUser(@Body userData: UserInfo): Call<UserInfo>
}