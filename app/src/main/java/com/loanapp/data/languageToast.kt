package com.loanapp.data

import org.intellij.lang.annotations.Language
import java.util.*

object languageToast {
    fun languageToast(): Boolean {
        return Locale.getDefault().language=="ru"
    }
}