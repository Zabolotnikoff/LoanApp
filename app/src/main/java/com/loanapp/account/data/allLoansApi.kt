package com.loanapp.account.data

import retrofit2.Call
import retrofit2.http.GET

interface allLoansApi {
    @GET("/loans/all")
    fun getAllLoans(): Call<List<AllLoans>>
}