package com.loanapp.login.presenter

import android.app.Activity
import android.widget.Toast
import com.loanapp.login.contract.ContractInterfaceSignInActivity
import com.loanapp.data.CheckNetwork
import com.loanapp.data.ValidateLoginPassword
import com.loanapp.login.model.SignInActivityModel

class SignInActivityPresenter(_view: ContractInterfaceSignInActivity.View) :
    ContractInterfaceSignInActivity.Presenter {

    private var view: ContractInterfaceSignInActivity.View = _view
    private var model: ContractInterfaceSignInActivity.Model = SignInActivityModel()

    init {
        view.initView()
    }

    override fun signIn(name: String, password: String, activity: Activity) {
        if (CheckNetwork.check(activity)) {
            if (ValidateLoginPassword.validateUserInfo(name, password, activity)) {
                model.signIn(name, password, activity)
                view.updateViewData()
            } else {
                Toast.makeText(activity, "Нет доступа к сети", Toast.LENGTH_SHORT).show()
            }
        }
    }

}