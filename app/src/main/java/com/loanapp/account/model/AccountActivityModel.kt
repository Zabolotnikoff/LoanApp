package com.loanapp.account.model

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.navigation.NavigationView
import com.loanapp.R
import com.loanapp.account.contract.ContractInterfaceAccountActivity
import com.loanapp.account.data.AllLoans
import com.loanapp.account.data.AllLoansViewHolder
import com.loanapp.account.data.allLoansApi
import com.loanapp.data.retrofit.RetrofitBuilder
import com.loanapp.createloan.ui.CreateLoanActivity
import com.loanapp.data.languageToast
import kotlinx.android.synthetic.main.account_activity.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import kotlin.collections.ArrayList

class AccountActivityModel : ContractInterfaceAccountActivity.Model {
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var actionBarToggle: ActionBarDrawerToggle
    private lateinit var navView: NavigationView
    var listLoans = ArrayList<AllLoans>()

    override fun onSupportNavigateUp(): Boolean {
        if (drawerLayout.isDrawerOpen(navView)) {
            drawerLayout.closeDrawer(navView)
            return true
        } else
            drawerLayout.openDrawer(navView)
        return true
    }

    override fun onBackPressed(activity: Activity) {
        if (this.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            this.drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            activity.finishAffinity()
        }
    }

    override fun drawNavigationView(activity: Activity) {
        drawerLayout = activity.findViewById(R.id.drawer_account_layout)
        actionBarToggle = ActionBarDrawerToggle(activity, drawerLayout, 0, 0)
        drawerLayout.addDrawerListener(actionBarToggle)
        actionBarToggle.syncState()
        navView = activity.findViewById(R.id.navView)
        navView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.take_loan -> {
                    val intent = Intent(activity, CreateLoanActivity::class.java)
                    intent.putExtra("Token", setIntentToken(activity))
                    activity.startActivity(intent)
                    true
                }
                R.id.writetotechnicalsupport -> {
                    if (languageToast.languageToast()){
                        Toast.makeText(activity, "Извините тех поддержка пока недоступна", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(activity, "Sorry, tech support is not available yet", Toast.LENGTH_SHORT).show()
                    }

                    true
                }
                else -> false
            }
        }
    }

    override fun setIntentToken(activity: Activity): String {
        val bundle = activity.intent.extras
        return bundle?.getString("Token").toString()
    }

    override fun getAllLoans(activity: Activity) {
        val getAllLoan =
            RetrofitBuilder.retrofitBuilderWithToken(setIntentToken(activity), activity)
                .create(allLoansApi::class.java)
        val call = getAllLoan.getAllLoans()
        call.enqueue(object : Callback<List<AllLoans>> {
            override fun onResponse(
                call: Call<List<AllLoans>>,
                response: Response<List<AllLoans>>
            ) {
                response.body()?.let { listLoans.addAll(it) }
                recyclerviewAllLoans(activity)
                Log.d("URLAll", listLoans.toString())
            }

            override fun onFailure(call: Call<List<AllLoans>>, t: Throwable) {
                Log.d("URLAll", t.toString())
            }
        })
    }

    override fun recyclerviewAllLoans(activity: Activity) {
        if (listLoans.isEmpty()) {
            activity.textView_emptyLoans.visibility = TextView.VISIBLE
        }
        val myAdapter = AllLoansViewHolder(listLoans)
        activity.recycler_allLoan.layoutManager = LinearLayoutManager(activity)
        activity.recycler_allLoan.adapter = myAdapter
    }


    override fun showChangeLang(activity: Activity) {

        val listItmes = arrayOf("English", "Русский")

        val mBuilder = AlertDialog.Builder(activity)
        mBuilder.setTitle("Choose Language")
        mBuilder.setSingleChoiceItems(listItmes, -1) { dialog, which ->
            if (which == 0) {
                setLocate("en", activity)
                activity.recreate()
            } else if (which == 1) {
                setLocate("ru", activity)
                activity.recreate()
            }

            dialog.dismiss()
        }
        val mDialog = mBuilder.create()

        mDialog.show()

    }

    fun setLocate(Lang: String, activity: Activity) {

        val locale = Locale(Lang)

        Locale.setDefault(locale)

        val config = Configuration()

        config.locale = locale
        activity.baseContext.resources.updateConfiguration(
            config,
            activity.baseContext.resources.displayMetrics
        )

        val editor = activity.getSharedPreferences("Settings", Context.MODE_PRIVATE).edit()
        editor.putString("My_Lang", Lang)
        editor.apply()
    }

    override fun loadLocate(activity: Activity) {
        val sharedPreferences = activity.getSharedPreferences("Settings", Activity.MODE_PRIVATE)
        val language = sharedPreferences.getString("My_Lang", "")
        setLocate(language!!, activity)
    }
}