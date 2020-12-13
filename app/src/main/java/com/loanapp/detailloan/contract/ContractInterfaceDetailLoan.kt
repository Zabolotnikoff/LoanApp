package com.loanapp.detailloan.contract

import android.app.Activity
import android.content.Intent
import android.os.Bundle

interface ContractInterfaceDetailLoan {
    interface View {
        fun initView()
        fun updateViewData()
    }

    interface Presenter {
        fun getinfoToken(activity: Activity)
    }

    interface Model {
        fun getinfoToken(activity: Activity)
    }
}