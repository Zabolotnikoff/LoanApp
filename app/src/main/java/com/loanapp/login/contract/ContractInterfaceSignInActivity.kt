package com.loanapp.login.contract

import android.app.Activity
import android.content.Context

interface ContractInterfaceSignInActivity {
    interface View {
        fun initView()
        fun updateViewData()
    }

    interface Presenter {
        fun signIn(name:String, password: String, activity: Activity)
    }

    interface Model {
        fun signIn(name:String, password: String, activity: Activity)
    }
}