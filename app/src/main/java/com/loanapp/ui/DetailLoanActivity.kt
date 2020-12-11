package com.loanapp.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.loanapp.R
import com.loanapp.data.getAllLoans.AllLoans
import com.loanapp.data.languageToast
import com.loanapp.data.loan.LoanInfo
import kotlinx.android.synthetic.main.deatail_loan_activity.*

class DetailLoanActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.deatail_loan_activity)
        getinfoToken()
    }

    @SuppressLint("SetTextI18n")
    private fun getinfoToken() {
        val bundle=intent.extras
        val allLoans = AllLoans(bundle?.getString("firstName")!!,
                bundle.getString("lastName")!!,
                bundle.getString("phoneNumber")!!,
                bundle.getLong("amount"),
                bundle.getDouble("percent"),
                bundle.getLong("period"),
                bundle.getString("date").toString(),
                bundle.getString("state").toString(),
                bundle.getLong("id"),
        )

        if (languageToast.languageToast()){
            textView_FirstName.text = "Имя: "+allLoans.firstName
            textView_lastName.text = "Фамилия: "+allLoans.lastName
            textView_phoneNumber.text = "Номер телефона: "+allLoans.phoneNumber
            textView_amount.text = "Сумма займа: "+allLoans.amount.toString()
            textView_percent.text = "Процент: "+allLoans.percent.toString()
            textView_period.text = "Период пользования займом: "+allLoans.period.toString()
            textView_date.text = "Дата оформления: "+allLoans.date
            when (allLoans.state)
            {
                "APPROVED" -> {
                    textView_state.text = "Статус: Займ одобрен"
                }
                "REJECTED" -> {
                    textView_state.text = "Статус: Займ отклонен"
                }
                "REGISTERED" -> {
                    textView_state.text = "Статус: Еще нет решения"
                }
                else -> {
                    textView_state.text = "Статус: "+allLoans.state
                }
            }
        } else {
            textView_FirstName.text = "Name: "+allLoans.firstName
            textView_lastName.text = "Last Name: "+allLoans.lastName
            textView_phoneNumber.text = "Phone number : "+allLoans.phoneNumber
            textView_amount.text = "Loan amount: "+allLoans.amount.toString()
            textView_percent.text = "Percent: "+allLoans.percent.toString()
            textView_period.text = "Loan use period: "+allLoans.period.toString()
            textView_date.text = "Loan date: "+allLoans.date
                    textView_state.text = allLoans.state
            }
        }
}