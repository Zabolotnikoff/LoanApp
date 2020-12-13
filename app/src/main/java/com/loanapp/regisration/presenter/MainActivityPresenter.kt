package com.loanapp.regisration.presenter


import android.app.Activity
import android.content.Context
import android.widget.Toast
import com.loanapp.regisration.contract.ContractInterfaceMainActivity
import com.loanapp.data.CheckNetwork
import com.loanapp.data.ValidateLoginPassword
import com.loanapp.regisration.model.MainActivityModel

class MainActivityPresenter(_view: ContractInterfaceMainActivity.View) :
    ContractInterfaceMainActivity.Presenter {


    private var view: ContractInterfaceMainActivity.View = _view
    private var model: ContractInterfaceMainActivity.Model = MainActivityModel()


    init {
        view.initView()
    }

    override fun registration(
        name: String,
        password: String,
        context: Context,
        activity: Activity
    ) {
        if (CheckNetwork.check(activity)) {
            if (ValidateLoginPassword.validateUserInfo(name, password, context)) {
                model.registration(name, password, context, activity)
                view.updateViewData()
            }
        } else {
            Toast.makeText(activity, "Нет доступа к сети", Toast.LENGTH_SHORT).show()
        }
    }

    override fun showChangeLang(activity: Activity) {
        model.showChangeLang(activity)
    }

    override fun loadLocate(activity: Activity) {
        model.loadLocate(activity)
    }


}