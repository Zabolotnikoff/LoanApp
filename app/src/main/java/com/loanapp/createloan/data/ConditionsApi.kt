package com.loanapp.createloan.data


import retrofit2.Call
import retrofit2.http.*

interface ConditionsApi {
    @GET("/loans/conditions")
    fun getConditions(): Call<Conditions>
}