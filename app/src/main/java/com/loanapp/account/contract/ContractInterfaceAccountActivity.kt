package com.loanapp.account.contract

import android.app.Activity

interface ContractInterfaceAccountActivity {
    interface View {
        fun initView()
        fun updateViewData()
    }

    interface Presenter {
        fun onSupportNavigateUp(): Boolean
        fun onBackPressed(activity: Activity)
        fun drawNavigationView(activity: Activity)
        fun setIntentToken(activity: Activity): String
        fun getAllLoans(activity: Activity)
        fun recyclerviewAllLoans(activity: Activity)
        fun showChangeLang(activity: Activity)
        fun loadLocate(activity: Activity)
    }

    interface Model {
        fun onSupportNavigateUp(): Boolean
        fun onBackPressed(activity: Activity)
        fun drawNavigationView(activity: Activity)
        fun setIntentToken(activity: Activity): String
        fun getAllLoans(activity: Activity)
        fun recyclerviewAllLoans(activity: Activity)
        fun showChangeLang(activity: Activity)
        fun loadLocate(activity: Activity)
    }
}