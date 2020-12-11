package com.loanapp.data.getAllLoans

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.loanapp.R
import com.loanapp.data.languageToast
import com.loanapp.ui.DetailLoanActivity
import kotlinx.android.synthetic.main.deatail_loan_activity.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import kotlin.system.measureTimeMillis

class AllLoansViewHolder(var list: List<AllLoans>) : RecyclerView.Adapter<AllLoansViewHolder.MainHolder>(){

    var context: Context? = null
    var listLoans = ArrayList<AllLoans>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recycler_item, parent, false)
        context = parent.context
        return MainHolder(view)
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        holder.bind(list[position])
        listLoans.add(list[position])

    }

    override fun getItemCount(): Int {
       return list.size
    }

    inner class MainHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val amount = itemView.findViewById<TextView>(R.id.amountTextViewItem)
        private val date = itemView.findViewById<TextView>(R.id.dateTextViewItem)
        private val state = itemView.findViewById<TextView>(R.id.stateTextViewItem)

        @SuppressLint("SetTextI18n")
        fun bind(allLoans: AllLoans) {
            amount.text = allLoans.amount.toString()+" Р"
            date.text = allLoans.date.substring(0, allLoans.date.indexOf('T'))
            if (languageToast.languageToast()){
                when (allLoans.state) {
                    "APPROVED" -> {
                        state.text = "Займ одобрен"
                    }
                    "REJECTED" -> {
                        state.text = "Займ отклонен"
                    }
                    "REGISTERED" -> {
                        state.text = "Еще нет решения"
                    }
                    else -> {
                        state.text = allLoans.state
                    }
                }
            } else {
                state.text = allLoans.state
            }


        }

        init {
            itemView.setOnClickListener {
                Log.d("URLDET", listLoans[adapterPosition].toString())
                val intent = Intent(itemView.context, DetailLoanActivity::class.java)
                intent.putExtra("firstName", listLoans[adapterPosition].firstName)
                intent.putExtra("lastName", listLoans[adapterPosition].lastName)
                intent.putExtra("phoneNumber", listLoans[adapterPosition].phoneNumber)
                intent.putExtra("amount", listLoans[adapterPosition].amount)
                intent.putExtra("percent", listLoans[adapterPosition].percent)
                intent.putExtra("period", listLoans[adapterPosition].period)
                intent.putExtra("date", listLoans[adapterPosition].date)
                intent.putExtra("state", listLoans[adapterPosition].state)
                intent.putExtra("id", listLoans[adapterPosition].id)
                itemView.context.startActivity(intent)
            }


            }

    }
}