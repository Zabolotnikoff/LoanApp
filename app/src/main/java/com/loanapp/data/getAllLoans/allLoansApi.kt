package com.loanapp.data.getAllLoans

import com.loanapp.data.loan.Conditions
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST

interface allLoansApi {
    @GET("/loans/all")
    fun getAllLoans(): Call<List<AllLoans>>
}