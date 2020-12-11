package com.loanapp.data

import android.content.Context
import android.util.Patterns
import android.widget.Toast
import com.loanapp.R
import java.util.*

object ValidateLoginPassword {
    fun validateUserInfo(name: String, password: String, context: Context): Boolean {
        if (Patterns.EMAIL_ADDRESS.matcher(name).matches() && password.length > 4) {
            return true
        } else if (password.length < 5) {
            if (languageToast.languageToast()){
                Toast.makeText(context, "Пароль должен быть не менее 5 символов", Toast.LENGTH_SHORT).show()
                return false
            } else
                Toast.makeText(context, "Password must be at least 5 characters", Toast.LENGTH_SHORT).show()
            return false
        } else if (!Patterns.EMAIL_ADDRESS.matcher(name).matches()) {
            if (Locale.getDefault().language=="ru"){
                Toast.makeText(context,
                        "Введите корректный email", Toast.LENGTH_SHORT).show()
                return false
            } else
                Toast.makeText(context, "Please enter a valid email", Toast.LENGTH_SHORT).show()
            return false

        }
        return false
    }
}