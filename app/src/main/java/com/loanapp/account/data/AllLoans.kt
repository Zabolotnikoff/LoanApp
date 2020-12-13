package com.loanapp.account.data

data class AllLoans(
        val firstName: String,
        val lastName: String,
        val phoneNumber: String,
        val amount: Long,
        val percent: Double,
        val period: Long,
        val date: String,
        val state: String,
        val id: Long
)