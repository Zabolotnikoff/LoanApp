package com.loanapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import com.loanapp.R
import com.loanapp.data.registration.LoanApiServicesRegistration
import com.loanapp.data.UserInfo
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.regex.Pattern

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        buttonRegistration.setOnClickListener {
            registration()
        }
    }

    private fun registration(){
        val loanApiServicesRegistration = retrofitBuilder().create(LoanApiServicesRegistration::class.java)
        val call = loanApiServicesRegistration.createUser(userInfo())
        call.enqueue(object : Callback<UserInfo>{
            override fun onResponse(call: Call<UserInfo>, response: Response<UserInfo>) {
                Log.d("URLREG", response.code().toString())
            }

            override fun onFailure(call: Call<UserInfo>, t: Throwable) {
            }

        })
    }

    private fun retrofitBuilder(): Retrofit {
        val retrofitt = Retrofit.Builder()
                .baseUrl("http://focusapp-env.eba-xm2atk2z.eu-central-1.elasticbeanstalk.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        return retrofitt
    }
    private fun userInfo(name: String, password:String): UserInfo{
        return UserInfo(name, password)
    }
    private fun validateUserInfo(): Boolean{
        val name = editName.text.toString()
        val password = editPassword.text.toString()
        if (Patterns.EMAIL_ADDRESS.matcher(name).matches()&&name.length>2&&password.length>3){
            return true
        } else
            return false
    }
}