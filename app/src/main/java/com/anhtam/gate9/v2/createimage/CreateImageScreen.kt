package com.anhtam.gate9.v2.createimage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.anhtam.gate9.R
import com.anhtam.gate9.v2.main.DaggerNavigationFragment
import kotlinx.android.synthetic.main.create_image_screen.*

class CreateImageScreen : DaggerNavigationFragment() {

    companion object {
        fun newInstance() = CreateImageScreen()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        activity?.window?.statusBarColor = ContextCompat.getColor(context!!, R.color.color_main_blue)
        return inflater.inflate(R.layout.create_image_screen, container, false)
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