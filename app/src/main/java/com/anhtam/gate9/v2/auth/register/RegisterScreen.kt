package com.anhtam.gate9.v2.auth.register

import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.anhtam.gate9.R
import com.anhtam.gate9.v2.main.DaggerNavigationFragment
import kotlinx.android.synthetic.main.register_screen.*


class RegisterScreen : DaggerNavigationFragment() {

    companion object{
        fun newInstance() = RegisterScreen()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.register_screen, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setColorText()
        events()
    }

    override fun statusColor() = R.color.color_main_orange

    private fun setColorText(){
        val styledText = "<font color='red'>Note: </font> Trong vào 48h, bạn cần vào Email đã đăng ký để kích hoạt tài khoản trước khi sử dụng"
        tvNote.setText(Html.fromHtml(styledText), TextView.BufferType.SPANNABLE)
    }

    private fun events(){
        tvBackRegister.setOnClickListener { navigation?.back() }
    }
}
