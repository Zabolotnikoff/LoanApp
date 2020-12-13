package com.loanapp.account.ui

import android.content.res.Configuration
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.loanapp.R
import com.loanapp.account.contract.ContractInterfaceAccountActivity
import com.loanapp.account.presenter.AccountActivityPresenter

class AccountActivity : AppCompatActivity(), ContractInterfaceAccountActivity.View {

    private var presenter: AccountActivityPresenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        presenter = AccountActivityPresenter(this)
        presenter?.loadLocate(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.account_activity)
        presenter?.drawNavigationView(this)
        presenter?.setIntentToken(this)
        presenter?.getAllLoans(this)
        presenter?.loadLocate(this)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_toolbar, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == R.id.change_language) {
            presenter?.showChangeLang(this)
            return true
        } else if (id == R.id.change_theme){
            when (resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) {
                Configuration.UI_MODE_NIGHT_YES ->
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                Configuration.UI_MODE_NIGHT_NO ->
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        }

    }
        return super.onOptionsItemSelected(item)
    }

    override fun initView() {}

    override fun updateViewData() {}

    override fun onSupportNavigateUp(): Boolean {
        presenter?.onSupportNavigateUp()
        return true
    }

    override fun onBackPressed() {
        presenter?.onBackPressed(this)
    }

}