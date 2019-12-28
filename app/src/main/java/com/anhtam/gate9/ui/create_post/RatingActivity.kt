package com.anhtam.gate9.ui.create_post

import android.os.Bundle
import com.anhtam.gate9.R
import com.anhtam.gate9.ui.base.BaseActivity
import com.anhtam.gate9.utils.debounceClick
import kotlinx.android.synthetic.main.activity_new_rate.*

class RatingActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_rate)
        initControls()
    }

    private fun initControls(){
        tvCancel.debounceClick { onBackPressed() }
    }
}
