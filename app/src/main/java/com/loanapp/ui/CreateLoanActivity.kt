package com.loanapp.ui

import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.loanapp.R
import com.loanapp.data.UserInfo
import com.loanapp.data.loan.Conditions
import com.loanapp.data.loan.ConditionsApi
import com.loanapp.data.retrofit.RetrofitBuilder
import com.loanapp.data.signIn.LoanApiSignIn
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CreateLoanActivity:AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.create_loan_activity)
        conditions()

    }



    private fun setIntentToken(): String {
        val bundle=intent.extras
        return bundle?.getString("Token").toString()
    }

    private fun conditions(){
        val loanApiConditions = RetrofitBuilder.retrofitBuilderWithToken(setIntentToken()).create(ConditionsApi::class.java)
        val call = loanApiConditions.getConditions()
        call.enqueue(object : Callback<Conditions>{
            override fun onResponse(call: Call<Conditions>, response: Response<Conditions>) {
                Log.d("URLCON", response.body().toString())
            }

            override fun onFailure(call: Call<Conditions>, t: Throwable) {
                Log.d("URLCON", t.message.toString())
            }
        })
    }
}