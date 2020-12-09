package com.loanapp.data.getAllLoans

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.loanapp.R
import javax.security.auth.callback.Callback

class AllLoansViewHolder(var list: List<AllLoans>) : RecyclerView.Adapter<AllLoansViewHolder.MainHolder>(){



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
            = MainHolder(LayoutInflater.from(parent.context).inflate(R.layout.recycler_item, parent, false))

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int {
       return list.size
    }

    inner class MainHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val amount = itemView.findViewById<TextView>(R.id.amountTextViewItem)
        private val date = itemView.findViewById<TextView>(R.id.dateTextViewItem)

        fun bind(allLoans: AllLoans) {
            amount.text = allLoans.amount.toString()
            date.text = allLoans.date
        }
    }
}