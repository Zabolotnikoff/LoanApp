package com.loanapp.infosliderloan.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.loanapp.R
import com.loanapp.infosliderloan.contract.ContractInterfaceInfoSliderActivity
import com.loanapp.infosliderloan.presenter.SliderActivityPresenter
import kotlinx.android.synthetic.main.info_slider_activity.*

class InfoSliderActivity : AppCompatActivity(), ContractInterfaceInfoSliderActivity.View {
    private var presenter: SliderActivityPresenter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.info_slider_activity)
        presenter = SliderActivityPresenter(this)
        presenter?.infoSliderCurrent(this)

        nextButton.setOnClickListener {
            presenter?.sliderClick(this)
        }

        textViewSkip.setOnClickListener {
            presenter?.Skip(this)
        }

    }

    override fun initView() {

    }

    override fun updateViewData() {

    }


}