package com.loanapp.infosliderloan.model

import android.app.Activity
import android.content.Intent
import android.widget.Toast
import com.loanapp.R
import com.loanapp.infosliderloan.contract.ContractInterfaceInfoSliderActivity
import com.loanapp.data.CheckNetwork
import com.loanapp.infosliderloan.data.InfoSlider
import com.loanapp.infosliderloan.data.InfoSliderAdapter
import com.loanapp.account.ui.AccountActivity
import kotlinx.android.synthetic.main.info_slider_activity.*

class InfoSliderActivityModel : ContractInterfaceInfoSliderActivity.Model {
    var infoSliderAdapter: InfoSliderAdapter? = null
    override fun infoSliderCurrent(activity: Activity) {
        infoSliderAdapter = InfoSliderAdapter(
            listOf(
                InfoSlider(
                    activity.getString(R.string.Title1),
                    activity.getString(R.string.Description1),
                    R.drawable.first
                ),
                InfoSlider(
                    activity.getString(R.string.Title2),
                    activity.getString(R.string.Description2),
                    R.drawable.two
                )
            )
        )

        activity.infoSliderPager.adapter = infoSliderAdapter
    }

    override fun sliderClick(activity: Activity) {
        when {
            activity.infoSliderPager.currentItem + 1 < infoSliderAdapter?.itemCount!! -> {
                activity.infoSliderPager.currentItem += 1
            }
            else -> {
                if (CheckNetwork.check(activity)) {
                    val intent = Intent(activity, AccountActivity::class.java)
                    intent.putExtra("Token", setIntentToken(activity))
                    activity.startActivity(intent)
                } else {
                    Toast.makeText(activity, "Нет доступа к сети", Toast.LENGTH_SHORT).show()
                }

            }
        }
    }

    private fun setIntentToken(activity: Activity): String {
        val bundle = activity.intent.extras
        return bundle?.getString("Token").toString()
    }

    override fun Skip(activity: Activity) {
        val intent = Intent(activity, AccountActivity::class.java)
        intent.putExtra("Token", setIntentToken(activity))
        activity.startActivity(intent)
    }
}