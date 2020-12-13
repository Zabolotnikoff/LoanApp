package com.loanapp.createloan.presenter

import android.app.Activity
import com.loanapp.createloan.contract.ContractInterfaceCreateLoan
import com.loanapp.data.CheckNetwork
import com.loanapp.createloan.model.CreateLoanActivityModel

class CreateLoanPresenter(_view: ContractInterfaceCreateLoan.View) :
    ContractInterfaceCreateLoan.Presenter {

    private var view: ContractInterfaceCreateLoan.View = _view
    private var model: ContractInterfaceCreateLoan.Model = CreateLoanActivityModel()


    init {
        view.initView()
    }


    override fun setIntentToken(activity: Activity): String {
        return model.setIntentToken(activity)
    }

    override fun conditions(activity: Activity) {
        model.conditions(activity)
    }

    override fun choiceAmount(activity: Activity): Int {
        return model.choiceAmount(activity)
    }

    override fun createLoan(activity: Activity) {
        if (CheckNetwork.check(activity)) {
            model.createLoan(activity)
        }

    }

    override fun validateInfo(activity: Activity): Boolean {
        return model.validateInfo(activity)
    }
}