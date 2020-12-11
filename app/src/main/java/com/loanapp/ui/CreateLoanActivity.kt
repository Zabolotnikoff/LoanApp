package com.loanapp.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.SeekBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.loanapp.R
import com.loanapp.data.languageToast
import com.loanapp.data.loan.Conditions
import com.loanapp.data.loan.ConditionsApi
import com.loanapp.data.loan.CreateLoanApi
import com.loanapp.data.loan.LoanInfo
import com.loanapp.data.retrofit.RetrofitBuilder
import kotlinx.android.synthetic.main.create_loan_activity.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CreateLoanActivity:AppCompatActivity() {
    var maxAmmount: Int? = null
    var loanInfo: LoanInfo? = null
    var percent: Double? = null
    var period: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.create_loan_activity)
        conditions()
        choiceAmount()
        createLoan()
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
                maxAmmount = response.body()?.maxAmount
                percent = response.body()?.percent
                period = response.body()?.period
                textView_maxAmount.append(maxAmmount.toString())
                textView_percentAmount.append(percent.toString())
                textView_periodAmount.append(period.toString())
                Log.d("URLCON", response.toString())
            }

            override fun onFailure(call: Call<Conditions>, t: Throwable) {
                Log.d("URLCON", t.message.toString())
            }
        })
    }
    private fun choiceAmount(): Int {
        val seekBar: SeekBar = findViewById(R.id.seekbar_amount)
        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                seekBar?.max = maxAmmount!!
                textView_amount_seekbar.text = seekBar?.progress.toString()
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }
            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                textView_amount_seekbar.text = seekBar?.progress.toString()
            }
        })

        return seekBar.progress
    }
    private fun createLoan(){
        button_createLoan.setOnClickListener {
            loanInfo = LoanInfo(choiceAmount(), editText_firstName.text.toString(),
                    editText_lastName.text.toString(), percent,
                    period, editText_phoneNumber.text.toString())
            if (validateInfo()){
                val createLoanApi = RetrofitBuilder.retrofitBuilderWithToken(setIntentToken()).create(CreateLoanApi::class.java)
                val call = createLoanApi.createLoan(loanInfo!!)
                call.enqueue(object : Callback<LoanInfo>{
                    override fun onResponse(call: Call<LoanInfo>, response: Response<LoanInfo>) {
                        if (response.code()==200){
                            //Toast.makeText(applicationContext, "Одобрено", Toast.LENGTH_SHORT).show()
                            val intent = Intent(this@CreateLoanActivity, AccountActivity::class.java)
                            intent.putExtra("Token", setIntentToken())
                            startActivity(intent)
                        }
                    }
                    override fun onFailure(call: Call<LoanInfo>, t: Throwable) {
                        TODO("Not yet implemented")
                    }
                })
            }
        }
    }
    private fun validateInfo() : Boolean{
        if(languageToast.languageToast()){
            when {
                choiceAmount()==0 -> {
                    Toast.makeText(applicationContext, "Выберете сумму", Toast.LENGTH_SHORT).show()
                    return false
                }
                editText_firstName.text.isEmpty() -> {
                    Toast.makeText(applicationContext, "Введите ваше имя", Toast.LENGTH_SHORT).show()
                    return false
                }
                editText_lastName.text.isEmpty() -> {
                    Toast.makeText(applicationContext, "Введите вашу фамилию", Toast.LENGTH_SHORT).show()
                    return false
                }
                editText_phoneNumber.text.isEmpty() -> {
                    Toast.makeText(applicationContext, "Введите ваш номер телефона", Toast.LENGTH_SHORT).show()
                    return false
                }
                editText_phoneNumber.text.length<10 -> {
                    Toast.makeText(applicationContext, "Номер телефона должен быть не менее 10 цифр", Toast.LENGTH_SHORT).show()
                    return false
                }
            }
        } else {
            when {
                choiceAmount() == 0 -> {
                    Toast.makeText(applicationContext, "Choose amount", Toast.LENGTH_SHORT).show()
                    return false
                }
                editText_firstName.text.isEmpty() -> {
                    Toast.makeText(applicationContext, "Enter your name", Toast.LENGTH_SHORT).show()
                    return false
                }
                editText_lastName.text.isEmpty() -> {
                    Toast.makeText(applicationContext, "Please enter your last name", Toast.LENGTH_SHORT).show()
                    return false
                }
                editText_phoneNumber.text.isEmpty() -> {
                    Toast.makeText(applicationContext, "Enter your phone number", Toast.LENGTH_SHORT).show()
                    return false
                }
                editText_phoneNumber.text.length < 10 -> {
                    Toast.makeText(applicationContext, "Phone number must be at least 10 digits", Toast.LENGTH_SHORT).show()
                    return false
                }
            }
        }
        return true
    }
}