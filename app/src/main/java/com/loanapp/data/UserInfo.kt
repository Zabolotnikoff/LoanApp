package com.loanapp.data

import com.google.gson.annotations.SerializedName

data class UserInfo (
        @SerializedName("name")
        val name: String?,
        @SerializedName("password")
        val password: String?
)