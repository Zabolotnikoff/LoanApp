package com.loanapp.regisration.ui

import android.content.Intent
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.app.AppCompatDelegate
import com.loanapp.R
import com.loanapp.regisration.contract.ContractInterfaceMainActivity
import com.loanapp.login.ui.SignInActivity
import com.loanapp.regisration.presenter.MainActivityPresenter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), ContractInterfaceMainActivity.View {

    private var presenter: MainActivityPresenter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        presenter = MainActivityPresenter(this)
        presenter?.loadLocate(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val textViewClick = findViewById<TextView>(R.id.textViewSignIn)

        textViewClick.setOnClickListener {
            val intent = Intent(this, SignInActivity::class.java)
            startActivity(intent)
        }

        buttonRegistration.setOnClickListener {
            val name = editName.text
            val password = editPassword.text
            presenter?.registration(
                name.toString(),
                password.toString(),
                context = applicationContext,
                this
            )
        }

    }

    override fun initView() {}

    override fun updateViewData() {}

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_toolbar, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == R.id.change_language) {
            presenter?.showChangeLang(this)
            return true
        } else if (id == R.id.change_theme) {
            when (resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) {
                Configuration.UI_MODE_NIGHT_YES ->
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                Configuration.UI_MODE_NIGHT_NO ->
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            }
        }
        return super.onOptionsItemSelected(item)
    }

}