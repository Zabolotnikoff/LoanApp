package com.loanapp.login.model

import android.app.Activity
import android.content.Intent
import android.util.Log
import android.widget.Toast
import com.loanapp.login.contract.ContractInterfaceSignInActivity
import com.loanapp.data.user.UserInfo
import com.loanapp.data.ValidateLoginPassword
import com.loanapp.data.languageToast
import com.loanapp.data.retrofit.RetrofitBuilder
import com.loanapp.login.data.LoanApiSignIn
import com.loanapp.infosliderloan.ui.InfoSliderActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignInActivityModel : ContractInterfaceSignInActivity.Model {
    override fun signIn(name: String, password: String, activity: Activity) {
        val loanApiSignIn = RetrofitBuilder.retrofitBuilder().create(LoanApiSignIn::class.java)
        if (ValidateLoginPassword.validateUserInfo(name, password, activity)) {
            val call = loanApiSignIn.signIn(UserInfo(name, password))
            call.enqueue(object : Callback<String> {
                override fun onResponse(call: Call<String>, response: Response<String>) {
                    Log.d("URLSIGN", response.toString())
                    when {
                        response.code() == 200 -> {
                            val intent = Intent(activity, InfoSliderActivity::class.java)
                            intent.putExtra("Token", response.body())
                            activity.startActivity(intent)
                        }
                        response.code() == 404 -> {
                            if (languageToast.languageToast()) {
                                Toast.makeText(
                                    activity,
                                    "Логин или пароль введен не правильно", Toast.LENGTH_LONG
                                ).show()
                            } else {
                                Toast.makeText(
                                    activity,
                                    "Login or password entered incorrectly", Toast.LENGTH_LONG
                                ).show()
                            }

                        }
                        else -> {
                            if (languageToast.languageToast()) {
                                Toast.makeText(
                                    activity,
                                    "Попробуйте позже или обратитесь в техподдержку",
                                    Toast.LENGTH_LONG
                                ).show()
                            } else {
                                Toast.makeText(
                                    activity,
                                    "Try again later or contact technical support",
                                    Toast.LENGTH_LONG
                                ).show()
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