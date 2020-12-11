package com.loanapp.ui

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.Toolbar
import com.loanapp.R
import com.loanapp.data.registration.LoanApiServicesRegistration
import com.loanapp.data.UserInfo
import com.loanapp.data.ValidateLoginPassword
import com.loanapp.data.languageToast
import com.loanapp.data.retrofit.RetrofitBuilder
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loadLocate()
        setContentView(R.layout.activity_main)

        buttonRegistration.setOnClickListener {
            registration()
        }

        val textViewClick = findViewById<TextView>(R.id.textViewSignIn)
        textViewClick.setOnClickListener {
            val intent = Intent(this, SignInActivity::class.java)
            startActivity(intent)
        }

    }

    private fun registration() {
        val loanApiServicesRegistration = RetrofitBuilder.retrofitBuilder().create(LoanApiServicesRegistration::class.java)
        val name = editName.text.toString()
        val password = editPassword.text.toString()
        if (ValidateLoginPassword.validateUserInfo(name, password, applicationContext)) {
            val call = loanApiServicesRegistration.createUser(UserInfo(name, password))
            call.enqueue(object : Callback<UserInfo> {
                override fun onResponse(call: Call<UserInfo>, response: Response<UserInfo>) {
                    Log.d("URLREG", response.code().toString())
                    when {
                        response.code() == 200 -> {
                            if (languageToast.languageToast()){
                                Toast.makeText(applicationContext,
                                        "Вы успешно зарегестрированы", Toast.LENGTH_LONG).show()
                            } else {
                                Toast.makeText(applicationContext, "You are successfully registered", Toast.LENGTH_LONG).show()
                            }
                            val intent = Intent(this@MainActivity, SignInActivity::class.java)
                            startActivity(intent)
                        }
                        response.code()==400 -> {
                            if (languageToast.languageToast()){
                                Toast.makeText(applicationContext,
                                        "Вы уже были зарегестрированы ранее", Toast.LENGTH_LONG).show()
                            } else {
                                Toast.makeText(applicationContext,
                                        "Вы уже были зарегестрированы ранее", Toast.LENGTH_LONG).show()
                            }

                        }
                        else -> {
                            if (languageToast.languageToast()){
                                Toast.makeText(applicationContext,
                                        "Что то пошло не так, попробуйте снова или обратитесь в техподдержку", Toast.LENGTH_LONG).show()
                            } else {
                                Toast.makeText(applicationContext,
                                        "Something went wrong, please try again or contact technical support", Toast.LENGTH_LONG).show()
                            }

                        }
                    }
                }

                override fun onFailure(call: Call<UserInfo>, t: Throwable) {
                }

            })
        }

    }

    private fun showChangeLang() {

        val listItmes = arrayOf("English","Русский")

        val mBuilder = AlertDialog.Builder(this@MainActivity)
        mBuilder.setTitle("Choose Language")
        mBuilder.setSingleChoiceItems(listItmes, -1) { dialog, which ->
            if (which == 0) {
                setLocate("en")
                recreate()
            } else if(which == 1) {
                setLocate("ru")
                recreate()
            }

            dialog.dismiss()
        }
        val mDialog = mBuilder.create()

        mDialog.show()

    }

    private fun setLocate(Lang: String) {

        val locale = Locale(Lang)

        Locale.setDefault(locale)

        val config = Configuration()

        config.locale = locale
        baseContext.resources.updateConfiguration(config, baseContext.resources.displayMetrics)

        val editor = getSharedPreferences("Settings", Context.MODE_PRIVATE).edit()
        editor.putString("My_Lang", Lang)
        editor.apply()
    }

    private fun loadLocate() {
        val sharedPreferences = getSharedPreferences("Settings", Activity.MODE_PRIVATE)
        val language = sharedPreferences.getString("My_Lang", "")
        setLocate(language!!)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_toolbar, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == R.id.change_language) {
            showChangeLang()
            return true
        }

        return super.onOptionsItemSelected(item)

    }
}