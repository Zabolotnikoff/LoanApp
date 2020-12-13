package com.loanapp.account.data

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.loanapp.R
import com.loanapp.data.languageToast
import com.loanapp.detailloan.ui.DetailLoanActivity
import kotlin.collections.ArrayList

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
                        state.setTextColor(Color.parseColor("#008000"))
                    }
                    "REJECTED" -> {
                        state.text = "Займ отклонен"
                        state.setTextColor(Color.parseColor("#B22222"))
                    }
                    "REGISTERED" -> {
                        state.text = "Еще нет решения"
                        state.setTextColor(Color.parseColor("#FF8C00"))
                    }
                }
            } else {
                when (allLoans.state) {
                    "APPROVED" -> {
                        state.text = allLoans.state
                        state.setTextColor(Color.parseColor("#008000"))
                    }
                    "REJECTED" -> {
                        state.text = allLoans.state
                        state.setTextColor(Color.parseColor("#B22222"))
                    }
                    "REGISTERED" -> {
                        state.text = allLoans.state
                        state.setTextColor(Color.parseColor("#FF8C00"))
                    }
                }
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