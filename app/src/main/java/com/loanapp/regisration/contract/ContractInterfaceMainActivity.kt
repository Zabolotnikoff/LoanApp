package com.loanapp.regisration.contract

import android.app.Activity
import android.content.Context

interface ContractInterfaceMainActivity {

    interface View {
        fun initView()
        fun updateViewData()
    }

    interface Presenter {
         fun registration(name: String, password: String, context: Context, activity: Activity)
         fun showChangeLang(activity: Activity)
         fun loadLocate(activity: Activity)
    }

    interface Model {
        fun registration(name: String, password: String, context: Context, activity: Activity)
        fun showChangeLang(activity: Activity)
        fun loadLocate(activity: Activity)
    }
}