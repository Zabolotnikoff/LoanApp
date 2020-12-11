package com.loanapp.ui

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.opengl.Visibility
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.ListAdapter
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
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
import java.util.*
import kotlin.collections.ArrayList

class AccountActivity : AppCompatActivity() {
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var actionBarToggle: ActionBarDrawerToggle
    private lateinit var navView: NavigationView
    var listLoans = ArrayList<AllLoans>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.account_activity)
        drawNavigationView()
        getAllLoans()
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
                R.id.darkTheme->{
                    when (resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) {
                        Configuration.UI_MODE_NIGHT_YES ->
                            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                        Configuration.UI_MODE_NIGHT_NO ->
                            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                    }
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
                response.body()?.let { listLoans.addAll(it) }
                recyclerviewAllLoans()
                Log.d("URLAll", listLoans.toString())
            }

            override fun onFailure(call: Call<List<AllLoans>>, t: Throwable) {
                Log.d("URLAll", t.toString())
            }
        })
    }
    private fun recyclerviewAllLoans(){
        if (listLoans.isEmpty()){
            textView_emptyLoans.visibility = TextView.VISIBLE
        }
        val myAdapter = AllLoansViewHolder(listLoans)
        recycler_allLoan.layoutManager = LinearLayoutManager(applicationContext)
        recycler_allLoan.adapter = myAdapter
    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_toolbar, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == R.id.change_language) {
            showChangeLang()
            return true
        }

        return super.onOptionsItemSelected(item)

    }

    private fun showChangeLang() {

        val listItmes = arrayOf("English","Русский")

        val mBuilder = AlertDialog.Builder(this@AccountActivity)
        mBuilder.setTitle("Choose Language")
        mBuilder.setSingleChoiceItems(listItmes, -1) { dialog, which ->
            if (which == 0) {
                setLocate("en")
                recreate()
            } else if(which == 1) {
                setLocate("ru")
                recreate()
            }

            dialog.dismiss()
        }
        val mDialog = mBuilder.create()

        mDialog.show()

    }

    private fun setLocate(Lang: String) {

        val locale = Locale(Lang)

        Locale.setDefault(locale)

        val config = Configuration()

        config.locale = locale
        baseContext.resources.updateConfiguration(config, baseContext.resources.displayMetrics)

        val editor = getSharedPreferences("Settings", Context.MODE_PRIVATE).edit()
        editor.putString("My_Lang", Lang)
        editor.apply()
    }

    private fun loadLocate() {
        val sharedPreferences = getSharedPreferences("Settings", Activity.MODE_PRIVATE)
        val language = sharedPreferences.getString("My_Lang", "")
        setLocate(language!!)
    }

}