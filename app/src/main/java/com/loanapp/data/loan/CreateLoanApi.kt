package com.loanapp.data.loan

import com.loanapp.data.UserInfo
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface CreateLoanApi {
    @Headers("Content-Type: application/json")
    @POST("/loans")
    fun createLoan(@Body loanInfo: LoanInfo): Call<LoanInfo>
}