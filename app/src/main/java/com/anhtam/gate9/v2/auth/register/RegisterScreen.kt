package com.anhtam.gate9.v2.auth.register

import android.os.Bundle
import android.text.Html
import android.view.View
import android.widget.TextView
import androidx.fragment.app.viewModels
import com.anhtam.gate9.R
import com.anhtam.gate9.v2.main.DaggerNavigationFragment
import kotlinx.android.synthetic.main.register_screen.*


class RegisterScreen : DaggerNavigationFragment(R.layout.register_screen) {

    companion object{
        fun newInstance() = RegisterScreen()
    }

    private val mViewModel: RegisterViewModel by viewModels { vmFactory }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setColorText()
        initEvents()
    }

    private fun initEvents(){
        btnRegister?.setOnClickListener {
            val email = etEmail?.text?.toString() ?: ""
            val password = etPassword?.text?.toString() ?: ""
            val name = etDisplayname?.text?.toString() ?: ""
            register(email, password, name)
        }
    }

    override fun menuRes() = R.menu.menu_chat_search_more

    override fun statusColor() = R.color.color_main_orange

    private fun setColorText(){
        val styledText = "<font color='red'>Note: </font> Trong vào 48h, bạn cần vào Email đã đăng ký để kích hoạt tài khoản trước khi sử dụng"
        tvNote.setText(Html.fromHtml(styledText), TextView.BufferType.SPANNABLE)
    }

    private fun register(email: String, password: String, name: String){
        if (mViewModel.validate(email, password, name)){
            // hop le
            mViewModel.registerWithEmail(email, password, name)
        }
    }
}
