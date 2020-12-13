package com.loanapp.infosliderloan.contract

import android.app.Activity

interface ContractInterfaceInfoSliderActivity {
    interface View {
        fun initView()
        fun updateViewData()
    }

    interface Presenter {
        fun infoSliderCurrent(activity: Activity)
        fun Skip(activity: Activity)
        fun sliderClick(activity: Activity)
    }

    interface Model {
        fun infoSliderCurrent(activity: Activity)
        fun Skip(activity: Activity)
        fun sliderClick(activity: Activity)
    }
}