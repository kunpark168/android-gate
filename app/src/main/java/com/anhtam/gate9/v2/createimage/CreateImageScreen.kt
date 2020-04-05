package com.anhtam.gate9.v2.createimage

import android.os.Bundle
import android.view.View
import com.anhtam.gate9.R
import com.anhtam.gate9.v2.main.DaggerNavigationFragment
import kotlinx.android.synthetic.main.create_image_screen.*

class CreateImageScreen : DaggerNavigationFragment(R.layout.create_image_screen) {

    companion object {
        fun newInstance() = CreateImageScreen()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        eventClicks()
    }

    private fun eventClicks() {
        tvCancel?.setOnClickListener {
            navigation?.back()
        }
    }
}