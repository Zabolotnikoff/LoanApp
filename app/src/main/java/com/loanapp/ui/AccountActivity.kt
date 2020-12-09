package com.loanapp.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ListAdapter
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.navigation.NavigationView
import com.loanapp.R
import com.loanapp.data.getAllLoans.AllLoans
import com.loanapp.data.getAllLoans.AllLoansViewHolder
import com.loanapp.data.getAllLoans.allLoansApi
import com.loanapp.data.loan.LoanInfo
import com.loanapp.data.retrofit.RetrofitBuilder
import kotlinx.android.synthetic.main.account_activity.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AccountActivity : AppCompatActivity() {
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var actionBarToggle: ActionBarDrawerToggle
    private lateinit var navView: NavigationView
    val listLoans = ArrayList<AllLoans>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.account_activity)
        drawNavigationView()
        getAllLoans()
        recyclerviewAllLoans()
    }
    override fun onSupportNavigateUp(): Boolean {
        if (drawerLayout.isDrawerOpen(navView)){
            drawerLayout.closeDrawer(navView)
            return true
        } else
        drawerLayout.openDrawer(navView)
        return true
    }
    override fun onBackPressed() {
        if (this.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            this.drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            finishAffinity()
        }
    }
    private fun drawNavigationView(){
        drawerLayout = findViewById(R.id.drawer_account_layout)
        actionBarToggle = ActionBarDrawerToggle(this, drawerLayout, 0, 0)
        drawerLayout.addDrawerListener(actionBarToggle)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        actionBarToggle.syncState()
        navView = findViewById(R.id.navView)
        navView.setNavigationItemSelectedListener { menuItem->
            when(menuItem.itemId){
                R.id.take_loan->{
                    val intent = Intent(applicationContext, CreateLoanActivity::class.java)
                    intent.putExtra("Token", setIntentToken())
                    startActivity(intent)
                    true
                }
                else -> false
            }
        }
    }
    private fun setIntentToken(): String {
        val bundle=intent.extras
        return bundle?.getString("Token").toString()
    }
    private fun getAllLoans(){
        val getAllLoan = RetrofitBuilder.retrofitBuilderWithToken(setIntentToken()).create(allLoansApi::class.java)
        val call = getAllLoan.getAllLoans()
        call.enqueue(object : Callback<List<AllLoans>>{
            override fun onResponse(call: Call<List<AllLoans>>, response: Response<List<AllLoans>>) {
                listLoans.addAll(response.body()!!)
                Log.d("URLAll", listLoans.toString())
            }

            override fun onFailure(call: Call<List<AllLoans>>, t: Throwable) {
                Log.d("URLAll", t.toString())
            }
        })
    }
    private fun recyclerviewAllLoans(){
        val myAdapter = AllLoansViewHolder(listLoans)
        recycler_allLoan.layoutManager = LinearLayoutManager(applicationContext)
        recycler_allLoan.adapter = myAdapter
    }
}