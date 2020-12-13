package com.loanapp.detailloan.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.loanapp.R
import com.loanapp.detailloan.contract.ContractInterfaceDetailLoan
import com.loanapp.detailloan.presenter.DetailActivityPresenter

class DetailLoanActivity : AppCompatActivity(), ContractInterfaceDetailLoan.View {
    private var presenter: DetailActivityPresenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        presenter = DetailActivityPresenter(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.deatail_loan_activity)
        presenter?.getinfoToken(this)
    }

    override fun initView() {

    }

    override fun updateViewData() {
    }

}