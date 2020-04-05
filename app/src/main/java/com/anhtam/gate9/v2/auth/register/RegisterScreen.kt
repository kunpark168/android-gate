package com.anhtam.gate9.v2.auth.register

import android.os.Bundle
import android.text.Html
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.anhtam.gate9.R
import com.anhtam.gate9.utils.DialogProgressUtils
import com.anhtam.gate9.utils.checkValidationRegistration
import com.anhtam.gate9.v2.auth.login.LoginScreen
import com.anhtam.gate9.v2.main.DaggerNavigationFragment
import kotlinx.android.synthetic.main.register_screen.*
import of.bum.network.helper.Resource


class RegisterScreen : DaggerNavigationFragment(R.layout.register_screen) {

    companion object {
        fun newInstance() = RegisterScreen()
    }

    private val mViewModel: RegisterViewModel by viewModels { vmFactory }
    private var mProgressDialog: DialogProgressUtils? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setColorText()
        initEvents()
    }

    private fun initEvents() {
        mProgressDialog = DialogProgressUtils(context, false)
        btnRegister?.setOnClickListener {
            val wrappedContext = context ?: return@setOnClickListener
            val errorRegister = wrappedContext.checkValidationRegistration(etEmail.text.toString(),
                    etPassword.text.toString(),
                    etCofirmPassword.text.toString(),
                    etDisplayname.text.toString())
            if (errorRegister == null) {
                val email = etEmail?.text?.toString() ?: ""
                val password = etPassword?.text?.toString() ?: ""
                val name = etDisplayname?.text?.toString() ?: ""
                register(email, password, name)
            } else {
                Toast.makeText(wrappedContext, errorRegister, Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun menuRes() = R.menu.menu_search_avatar_more

    private fun setColorText() {
        val styledText = "<font color='red'>Note: </font> Trong vào 48h, bạn cần vào Email đã đăng ký để kích hoạt tài khoản trước khi sử dụng"
        tvNote.setText(Html.fromHtml(styledText), TextView.BufferType.SPANNABLE)
    }

    private fun register(email: String, password: String, name: String) {
        mProgressDialog?.show()
        if (mViewModel.validate(email, password, name)) {
            mViewModel.registerWithEmail(email, password, name).observe(this, Observer {
                when (it) {
                    is Resource.Success -> {
                        if (mProgressDialog?.isShowing == true)
                            mProgressDialog?.dismiss()
                        navigation?.newRootFragment(LoginScreen.newInstance(true))

                    }
                    is Resource.Error -> {
                        if (mProgressDialog?.isShowing == true)
                            mProgressDialog?.dismiss()
                        Toast.makeText(activity, it.message, Toast.LENGTH_LONG).show()
                    }
                }
            })
        }
    }
}
