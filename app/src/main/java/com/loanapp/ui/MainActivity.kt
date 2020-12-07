package com.loanapp.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import com.loanapp.R
import com.loanapp.data.registration.LoanApiServicesRegistration
import com.loanapp.data.UserInfo
import com.loanapp.data.ValidateLoginPassword
import com.loanapp.data.retrofit.RetrofitBuilder
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
                    if (response.code() == 200) {
                        Toast.makeText(applicationContext,
                                "Вы успешно зарегестрированы", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<UserInfo>, t: Throwable) {
                }

            })
        }

    }
}