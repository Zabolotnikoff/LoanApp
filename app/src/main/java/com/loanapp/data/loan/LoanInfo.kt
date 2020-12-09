package com.loanapp.data.loan

data class LoanInfo(
    val amount: Int,
    val firstName: String,
    val lastName: String,
    val percent: Double?,
    val period: Int?,
    val phoneNumber: String
)