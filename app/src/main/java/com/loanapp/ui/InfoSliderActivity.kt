package com.loanapp.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.loanapp.R
import com.loanapp.data.infoSlider.InfoSlider
import com.loanapp.data.infoSlider.InfoSliderAdapter
import kotlinx.android.synthetic.main.info_slider_activity.*

class InfoSliderActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.info_slider_activity)
        val infoSliderAdapter = InfoSliderAdapter(
                listOf(
                        InfoSlider(getString(R.string.Title1),getString(R.string.Description1), R.drawable.first),
                        InfoSlider(getString(R.string.Title2), getString(R.string.Description2), R.drawable.two)
                )
        )
        infoSliderPager.adapter = infoSliderAdapter
        nextButton.setOnClickListener {
            if (infoSliderPager.currentItem + 1 < infoSliderAdapter.itemCount){
                infoSliderPager.currentItem += 1
            } else {
                val intent = Intent(applicationContext, AccountActivity::class.java)
                intent.putExtra("Token", setIntentToken())
                startActivity(intent)
            }
        }

        textViewSkip.setOnClickListener {
            val intent = Intent(applicationContext, AccountActivity::class.java)
            intent.putExtra("Token", setIntentToken())
            startActivity(intent)
        }
    }

    private fun setIntentToken(): String {
        val bundle=intent.extras
        return bundle?.getString("Token").toString()
    }
}