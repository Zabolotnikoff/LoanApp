package com.loanapp.detailloan.presenter

import android.app.Activity
import com.loanapp.detailloan.contract.ContractInterfaceDetailLoan
import com.loanapp.detailloan.model.DetailActivityModel

class DetailActivityPresenter(_view: ContractInterfaceDetailLoan.View) :
    ContractInterfaceDetailLoan.Presenter {
    private var view: ContractInterfaceDetailLoan.View = _view
    private var model: ContractInterfaceDetailLoan.Model = DetailActivityModel()


    init {
        view.initView()
    }


    override fun getinfoToken(activity: Activity) {
        model.getinfoToken(activity)
    }
}