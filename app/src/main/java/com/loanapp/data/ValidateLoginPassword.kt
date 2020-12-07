package com.loanapp.data

import android.content.Context
import android.util.Patterns
import android.widget.Toast

object ValidateLoginPassword {
    fun validateUserInfo(name: String, password: String, context: Context): Boolean {
        if (Patterns.EMAIL_ADDRESS.matcher(name).matches() && password.length > 4) {
            return true
        } else if (password.length < 5) {
            Toast.makeText(context,
                    "Пароль должен быть не менее 4 символов", Toast.LENGTH_SHORT).show()
            return false
        } else if (!Patterns.EMAIL_ADDRESS.matcher(name).matches()) {
            Toast.makeText(context,
                    "Введите корректный email", Toast.LENGTH_SHORT).show()
            return false
        }
        return false
    }
}