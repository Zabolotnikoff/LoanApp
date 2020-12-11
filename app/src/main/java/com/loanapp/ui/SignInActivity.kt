package com.loanapp.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.loanapp.R
import com.loanapp.data.UserInfo
import com.loanapp.data.ValidateLoginPassword
import com.loanapp.data.languageToast
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
                    Log.d("URLSIGN", response.toString())
                    when {
                        response.code()==200 -> {
                            val intent = Intent(applicationContext, InfoSliderActivity::class.java)
                            intent.putExtra("Token", response.body())
                            startActivity(intent)
                        }
                        response.code()==404 -> {
                            if(languageToast.languageToast()){
                                Toast.makeText(applicationContext,
                                        "Логин или пароль введен не правильно", Toast.LENGTH_LONG).show()
                            } else {
                                Toast.makeText(applicationContext,
                                                "Login or password entered incorrectly", Toast.LENGTH_LONG).show()
                            }

                        }
                        else -> {
                            if (languageToast.languageToast()){
                                Toast.makeText(applicationContext,
                                        "Попробуйте позже или обратитесь в техподдержку", Toast.LENGTH_LONG).show()
                            } else {
                                Toast.makeText(applicationContext,
                                                "Try again later or contact technical support", Toast.LENGTH_LONG).show()
                            }

                        }
                    }
                }

                override fun onFailure(call: Call<String>, t: Throwable) {
                    Log.d("URLSIGN", t.toString())
                }

            })
        }
    }
}