package com.loanapp.detailloan.model

import android.annotation.SuppressLint
import android.app.Activity
import com.loanapp.detailloan.contract.ContractInterfaceDetailLoan
import com.loanapp.account.data.AllLoans
import com.loanapp.data.languageToast
import kotlinx.android.synthetic.main.deatail_loan_activity.*

class DetailActivityModel : ContractInterfaceDetailLoan.Model {

    @SuppressLint("SetTextI18n")
    override fun getinfoToken(activity: Activity) {
        val bundle = activity.intent.extras
        val allLoans = AllLoans(
            bundle?.getString("firstName")!!,
            bundle.getString("lastName")!!,
            bundle.getString("phoneNumber")!!,
            bundle.getLong("amount"),
            bundle.getDouble("percent"),
            bundle.getLong("period"),
            bundle.getString("date").toString(),
            bundle.getString("state").toString(),
            bundle.getLong("id"),
        )

        if (languageToast.languageToast()) {
            activity.textView_FirstName.text = "Имя: " + allLoans.firstName
            activity.textView_lastName.text = "Фамилия: " + allLoans.lastName
            activity.textView_phoneNumber.text = "Номер телефона: " + allLoans.phoneNumber
            activity.textView_amount.text = "Сумма займа: " + allLoans.amount.toString()
            activity.textView_percent.text = "Процент: " + allLoans.percent.toString()
            activity.textView_period.text =
                "Период пользования займом: " + allLoans.period.toString()
            activity.textView_date.text = "Дата оформления: " + allLoans.date
            when (allLoans.state) {
                "APPROVED" -> {
                    activity.textView_state.text = "Статус: Займ одобрен"
                }
                "REJECTED" -> {
                    activity.textView_state.text = "Статус: Займ отклонен"
                }
                "REGISTERED" -> {
                    activity.textView_state.text = "Статус: Еще нет решения"
                }
                else -> {
                    activity.textView_state.text = "Статус: " + allLoans.state
                }
            }
        } else {
            activity.textView_FirstName.text = "Name: " + allLoans.firstName
            activity.textView_lastName.text = "Last Name: " + allLoans.lastName
            activity.textView_phoneNumber.text = "Phone number : " + allLoans.phoneNumber
            activity.textView_amount.text = "Loan amount: " + allLoans.amount.toString()
            activity.textView_percent.text = "Percent: " + allLoans.percent.toString()
            activity.textView_period.text = "Loan use period: " + allLoans.period.toString()
            activity.textView_date.text = "Loan date: " + allLoans.date
            activity.textView_state.text = allLoans.state
        }
    }
}