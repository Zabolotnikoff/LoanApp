package com.loanapp.login.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.loanapp.R
import com.loanapp.login.contract.ContractInterfaceSignInActivity
import com.loanapp.login.presenter.SignInActivityPresenter
import kotlinx.android.synthetic.main.sign_in_activity.*

class SignInActivity : AppCompatActivity(), ContractInterfaceSignInActivity.View {

    private var presenter: SignInActivityPresenter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.sign_in_activity)
        presenter = SignInActivityPresenter(this)
        buttonSignIn.setOnClickListener {
            val name = editNameSignIn.text.toString()
            val password = editPasswordSignIn.text.toString()
            presenter?.signIn(name, password, this)
        }

    }

    override fun initView() {

    }

    override fun updateViewData() {

    }
}