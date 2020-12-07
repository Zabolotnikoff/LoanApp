package com.loanapp.data.loan


import retrofit2.Call
import retrofit2.http.*

interface ConditionsApi {
    @GET("/loans/conditions")
    fun getConditions(): Call<Conditions>
}