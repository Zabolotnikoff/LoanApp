package com.loanapp.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.loanapp.R
import com.loanapp.data.UserInfo
import com.loanapp.data.ValidateLoginPassword
import com.loanapp.data.signIn.LoanApiSignIn
import com.loanapp.data.retrofit.RetrofitBuilder
import kotlinx.android.synthetic.main.sign_in_activity.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignInActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.sign_in_activity)

        buttonSignIn.setOnClickListener {
            signIn()
        }
    }

    private fun signIn(){
        val loanApiSignIn = RetrofitBuilder.retrofitBuilder().create(LoanApiSignIn::class.java)
        val name = editNameSignIn.text.toString()
        val password = editPasswordSignIn.text.toString()
        if (ValidateLoginPassword.validateUserInfo(name, password, applicationContext)){
            val call = loanApiSignIn.signIn(UserInfo(name, password))
            call.enqueue(object : Callback<String>{
                override fun onResponse(call: Call<String>, response: Response<String>) {
                    Log.d("URLSIGN", response.body().toString())
                    if (response.code()==200){
                        val intent = Intent(applicationContext, InfoSliderActivity::class.java)
                        intent.putExtra("Token", response.body())
                        startActivity(intent)
                    }
                }

                override fun onFailure(call: Call<String>, t: Throwable) {
                    Log.d("URLSIGN", t.toString())
                }

            })
        }
    }
}