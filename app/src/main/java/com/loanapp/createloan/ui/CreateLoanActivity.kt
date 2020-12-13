package com.loanapp.createloan.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.loanapp.R
import com.loanapp.createloan.contract.ContractInterfaceCreateLoan
import com.loanapp.createloan.presenter.CreateLoanPresenter
import kotlinx.android.synthetic.main.create_loan_activity.*


class CreateLoanActivity : AppCompatActivity(), ContractInterfaceCreateLoan.View {

    private var presenter: CreateLoanPresenter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        presenter = CreateLoanPresenter(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.create_loan_activity)
        presenter?.setIntentToken(this)

        presenter?.conditions(this)
        presenter?.choiceAmount(this)


        button_createLoan.setOnClickListener {
            presenter?.validateInfo(this)
            presenter?.createLoan(this)
        }
    }


    override fun initView() {

    }

    override fun updateViewData() {

    }
}