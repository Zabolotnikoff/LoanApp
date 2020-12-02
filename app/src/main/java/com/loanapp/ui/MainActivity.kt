package com.loanapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.widget.Toast
import com.loanapp.R
import com.loanapp.data.registration.LoanApiServicesRegistration
import com.loanapp.data.UserInfo
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

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
        val name = editName.text.toString()
        val password = editPassword.text.toString()
        if (validateUserInfo(name, password)){
            val call = loanApiServicesRegistration.createUser(userInfo(name, password))
            call.enqueue(object : Callback<UserInfo>{
                override fun onResponse(call: Call<UserInfo>, response: Response<UserInfo>) {
                    Log.d("URLREG", response.code().toString())
                    if (response.code()==200){
                        Toast.makeText(applicationContext,
                            "Вы успешно зарегестрированы", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<UserInfo>, t: Throwable) {
                }

            })
        }

    }

    private fun retrofitBuilder(): Retrofit {
        return Retrofit.Builder()
                .baseUrl("http://focusapp-env.eba-xm2atk2z.eu-central-1.elasticbeanstalk.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
    }
    private fun userInfo(name: String, password:String): UserInfo{
        return UserInfo(name, password)
    }
    private fun validateUserInfo(name:String, password: String): Boolean{
        if (Patterns.EMAIL_ADDRESS.matcher(name).matches()&&name.length>2&&password.length>3){
            return true
        } else if(password.length<3){
            Toast.makeText(applicationContext,
                "Пароль должен быть не менее 3 символов", Toast.LENGTH_SHORT).show()
            return false
        } else if (name.length<2){
            Toast.makeText(applicationContext,
                "Логин должен быть не менее 2 символов", Toast.LENGTH_SHORT).show()
            return false
        }
            return false
    }
}