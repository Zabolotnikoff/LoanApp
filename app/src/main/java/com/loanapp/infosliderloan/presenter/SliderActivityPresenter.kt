package com.loanapp.infosliderloan.presenter

import android.app.Activity
import com.loanapp.infosliderloan.contract.ContractInterfaceInfoSliderActivity
import com.loanapp.infosliderloan.model.InfoSliderActivityModel

class SliderActivityPresenter(_view: ContractInterfaceInfoSliderActivity.View) :
    ContractInterfaceInfoSliderActivity.Presenter {
    private var view: ContractInterfaceInfoSliderActivity.View = _view
    private var model: ContractInterfaceInfoSliderActivity.Model = InfoSliderActivityModel()

    init {
        view.initView()
    }


    override fun infoSliderCurrent(activity: Activity) {
        model.infoSliderCurrent(activity)
    }

    override fun Skip(activity: Activity) {
        model.Skip(activity)
    }

    override fun sliderClick(activity: Activity) {
        model.sliderClick(activity)
    }
}