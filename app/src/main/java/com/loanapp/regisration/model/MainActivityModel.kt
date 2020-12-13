package com.loanapp.regisration.model

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat.recreate
import com.loanapp.regisration.contract.ContractInterfaceMainActivity
import com.loanapp.data.user.UserInfo
import com.loanapp.data.languageToast
import com.loanapp.data.registration.LoanApiServicesRegistration
import com.loanapp.data.retrofit.RetrofitBuilder
import com.loanapp.login.ui.SignInActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*


class MainActivityModel : ContractInterfaceMainActivity.Model {


    override fun registration(
        name: String,
        password: String,
        context: Context,
        activity: Activity
    ) {

        val loanApiServicesRegistration =
            RetrofitBuilder.retrofitBuilder().create(LoanApiServicesRegistration::class.java)
        val call = loanApiServicesRegistration.createUser(UserInfo(name, password))
        call.enqueue(object : Callback<UserInfo> {
            override fun onResponse(call: Call<UserInfo>, response: Response<UserInfo>) {
                Log.d("URLREG", response.code().toString())
                when {
                    response.code() == 200 -> {
                        if (languageToast.languageToast()) {
                            Toast.makeText(
                                context,
                                "Вы успешно зарегестрированы", Toast.LENGTH_LONG
                            ).show()
                        } else {
                            Toast.makeText(
                                context,
                                "You are successfully registered",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                        val intent = Intent(activity, SignInActivity::class.java)
                        activity.startActivity(intent)
                    }
                    response.code() == 400 -> {
                        if (languageToast.languageToast()) {
                            Toast.makeText(
                                context,
                                "Вы уже были зарегестрированы ранее", Toast.LENGTH_LONG
                            ).show()
                        } else {
                            Toast.makeText(
                                context,
                                "Вы уже были зарегестрированы ранее", Toast.LENGTH_LONG
                            ).show()
                        }

                    }
                    else -> {
                        if (languageToast.languageToast()) {
                            Toast.makeText(
                                context,
                                "Что то пошло не так, попробуйте снова или обратитесь в техподдержку",
                                Toast.LENGTH_LONG
                            ).show()
                        } else {
                            Toast.makeText(
                                context,
                                "Something went wrong, please try again or contact technical support",
                                Toast.LENGTH_LONG
                            ).show()
                        }

                    }
                }
            }

            override fun onFailure(call: Call<UserInfo>, t: Throwable) {
            }

        })


    }

    override fun showChangeLang(activity: Activity) {

        val listItmes = arrayOf("English", "Русский")

        val mBuilder = AlertDialog.Builder(activity)
        mBuilder.setTitle("Choose Language")
        mBuilder.setSingleChoiceItems(listItmes, -1) { dialog, which ->
            if (which == 0) {
                setLocate("en", activity)
                recreate(activity)
            } else if (which == 1) {
                setLocate("ru", activity)
                recreate(activity)
            }

            dialog.dismiss()
        }
        val mDialog = mBuilder.create()
        mDialog.show()
    }

    fun setLocate(Lang: String, activity: Activity) {
        val locale = Locale(Lang)

        Locale.setDefault(locale)

        val config = Configuration()

        config.locale = locale
        activity.baseContext.resources.updateConfiguration(
            config,
            activity.baseContext.resources.displayMetrics
        )

        val editor =
            activity.baseContext.getSharedPreferences("Settings", Context.MODE_PRIVATE).edit()
        editor.putString("My_Lang", Lang)
        editor.apply()
    }

    override fun loadLocate(activity: Activity) {
        val sharedPreferences = activity.getSharedPreferences("Settings", Activity.MODE_PRIVATE)
        val language = sharedPreferences.getString("My_Lang", "")
        setLocate(language!!, activity)
    }


}