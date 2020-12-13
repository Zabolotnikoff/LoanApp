package com.loanapp.createloan.model

import android.app.Activity
import android.content.Intent
import android.util.Log
import android.widget.SeekBar
import android.widget.Toast
import com.loanapp.R
import com.loanapp.createloan.contract.ContractInterfaceCreateLoan
import com.loanapp.data.languageToast
import com.loanapp.createloan.data.Conditions
import com.loanapp.createloan.data.ConditionsApi
import com.loanapp.createloan.data.CreateLoanApi
import com.loanapp.data.loan.LoanInfo
import com.loanapp.data.retrofit.RetrofitBuilder
import com.loanapp.account.ui.AccountActivity
import kotlinx.android.synthetic.main.create_loan_activity.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.math.roundToInt

class CreateLoanActivityModel : ContractInterfaceCreateLoan.Model {
    var maxAmmount: Int? = null
    var loanInfo: LoanInfo? = null
    var percent: Double? = null
    var period: Int? = null

    override fun setIntentToken(activity: Activity): String {
        val bundle = activity.intent.extras
        return bundle?.getString("Token").toString()
    }

    override fun conditions(activity: Activity) {
        val loanApiConditions =
            RetrofitBuilder.retrofitBuilderWithToken(setIntentToken(activity), activity)
                .create(ConditionsApi::class.java)
        val call = loanApiConditions.getConditions()
        call.enqueue(object : Callback<Conditions> {
            override fun onResponse(call: Call<Conditions>, response: Response<Conditions>) {
                maxAmmount = response.body()?.maxAmount
                percent = response.body()?.percent
                period = response.body()?.period
                activity.textView_maxAmount.append(maxAmmount.toString())
                activity.textView_percentAmount.append(percent.toString())
                activity.textView_periodAmount.append(period.toString())
                Log.d("URLCON", response.toString())
            }

            override fun onFailure(call: Call<Conditions>, t: Throwable) {
                Log.d("URLCON", t.message.toString())
            }
        })
    }

    override fun choiceAmount(activity: Activity): Int {
        val seekBar: SeekBar = activity.findViewById(R.id.seekbar_amount)
        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                val stepSize = 100
                seekBar?.max = maxAmmount!!
                val prog: Int
                prog = (progress / stepSize).toDouble().roundToInt() * stepSize
                seekBar!!.progress = prog
                activity.textView_amount_seekbar.text = seekBar.progress.toString()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                activity.textView_amount_seekbar.text = seekBar?.progress.toString()
            }
        })

        return seekBar.progress
    }

    override fun createLoan(activity: Activity) {
        loanInfo = LoanInfo(
            choiceAmount(activity), activity.editText_firstName.text.toString(),
            activity.editText_lastName.text.toString(), percent,
            period, activity.editText_phoneNumber.text.toString()
        )
        if (validateInfo(activity)) {
            val createLoanApi =
                RetrofitBuilder.retrofitBuilderWithToken(setIntentToken(activity), activity)
                    .create(CreateLoanApi::class.java)
            val call = createLoanApi.createLoan(loanInfo!!)
            call.enqueue(object : Callback<LoanInfo> {
                override fun onResponse(call: Call<LoanInfo>, response: Response<LoanInfo>) {
                    if (response.code() == 200) {
                        val intent = Intent(activity, AccountActivity::class.java)
                        intent.putExtra("Token", setIntentToken(activity))
                        activity.startActivity(intent)
                    }
                }

                override fun onFailure(call: Call<LoanInfo>, t: Throwable) {
                }
            })
        }

    }

    override fun validateInfo(activity: Activity): Boolean {
        if (languageToast.languageToast()) {
            when {
                choiceAmount(activity) == 0 -> {
                    Toast.makeText(activity, "Выберете сумму", Toast.LENGTH_SHORT).show()
                    return false
                }
                activity.editText_firstName.text.isEmpty() -> {
                    Toast.makeText(activity, "Введите ваше имя", Toast.LENGTH_SHORT).show()
                    return false
                }
                activity.editText_lastName.text.isEmpty() -> {
                    Toast.makeText(activity, "Введите вашу фамилию", Toast.LENGTH_SHORT).show()
                    return false
                }
                activity.editText_phoneNumber.text.isEmpty() -> {
                    Toast.makeText(activity, "Введите ваш номер телефона", Toast.LENGTH_SHORT)
                        .show()
                    return false
                }
                activity.editText_phoneNumber.text.length < 10 -> {
                    Toast.makeText(
                        activity,
                        "Номер телефона должен быть не менее 10 цифр",
                        Toast.LENGTH_SHORT
                    ).show()
                    return false
                }
            }
        } else {
            when {
                choiceAmount(activity) == 0 -> {
                    Toast.makeText(activity, "Choose amount", Toast.LENGTH_SHORT).show()
                    return false
                }
                activity.editText_firstName.text.isEmpty() -> {
                    Toast.makeText(activity, "Enter your name", Toast.LENGTH_SHORT).show()
                    return false
                }
                activity.editText_lastName.text.isEmpty() -> {
                    Toast.makeText(activity, "Please enter your last name", Toast.LENGTH_SHORT)
                        .show()
                    return false
                }
                activity.editText_phoneNumber.text.isEmpty() -> {
                    Toast.makeText(activity, "Enter your phone number", Toast.LENGTH_SHORT).show()
                    return false
                }
                activity.editText_phoneNumber.text.length < 10 -> {
                    Toast.makeText(
                        activity,
                        "Phone number must be at least 10 digits",
                        Toast.LENGTH_SHORT
                    ).show()
                    return false
                }
            }
        }
        return true
    }
}