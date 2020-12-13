package com.loanapp.account.presenter

import android.app.Activity
import com.loanapp.account.contract.ContractInterfaceAccountActivity
import com.loanapp.account.model.AccountActivityModel

class AccountActivityPresenter(_view: ContractInterfaceAccountActivity.View) :
    ContractInterfaceAccountActivity.Presenter {
    private var view: ContractInterfaceAccountActivity.View = _view
    private var model: ContractInterfaceAccountActivity.Model = AccountActivityModel()


    init {
        view.initView()
    }

    override fun onSupportNavigateUp(): Boolean {
        return model.onSupportNavigateUp()
    }

    override fun onBackPressed(activity: Activity) {
        model.onBackPressed(activity)
    }

    override fun drawNavigationView(activity: Activity) {
        model.drawNavigationView(activity)
    }

    override fun setIntentToken(activity: Activity): String {
        return model.setIntentToken(activity)
    }

    override fun getAllLoans(activity: Activity) {
        model.getAllLoans(activity)
    }

    override fun recyclerviewAllLoans(activity: Activity) {
        model.recyclerviewAllLoans(activity)
    }

    override fun showChangeLang(activity: Activity) {
        model.showChangeLang(activity)
    }

    override fun loadLocate(activity: Activity) {
        model.loadLocate(activity)
    }


}