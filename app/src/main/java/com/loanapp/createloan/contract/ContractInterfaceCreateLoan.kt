package com.loanapp.createloan.contract

import android.app.Activity

interface ContractInterfaceCreateLoan {
    interface View {
        fun initView()
        fun updateViewData()
    }

    interface Presenter {
        fun setIntentToken(activity: Activity): String
        fun conditions(activity: Activity)
        fun choiceAmount(activity: Activity): Int
        fun createLoan(activity: Activity)
        fun validateInfo(activity: Activity) : Boolean
    }

    interface Model {
        fun setIntentToken(activity: Activity): String
        fun conditions(activity: Activity)
        fun choiceAmount(activity: Activity): Int
        fun createLoan(activity: Activity)
        fun validateInfo(activity: Activity) : Boolean
    }
}