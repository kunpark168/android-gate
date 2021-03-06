package com.anhtam.gate9.v2.auth.login

import android.os.Bundle
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.view.MotionEvent
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.anhtam.gate9.R
import com.anhtam.gate9.session.AuthResource
import com.anhtam.gate9.v2.auth.register.RegisterScreen
import com.anhtam.gate9.v2.lib.invisible
import com.anhtam.gate9.v2.lib.then
import com.anhtam.gate9.v2.lib.visible
import com.anhtam.gate9.v2.main.DaggerNavigationFragment
import com.anhtam.gate9.v2.main.home.HomeFragment
import kotlinx.android.synthetic.main.login_screen.*

class LoginScreen(private val isDirect: Boolean) : DaggerNavigationFragment(R.layout.login_screen) {

    companion object {
        fun newInstance(isDirect: Boolean) = LoginScreen(isDirect)
    }

    private val loginViewModel: LoginViewModel by viewModels { vmFactory }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        events()
        observers()
    }

    private fun observers(){
        mSessionManager.cachedAccessToken.observe(viewLifecycleOwner, Observer {
            when(it.status){
                AuthResource.AuthStatus.AUTHENTICATED -> {
                    hideProgress()
                    if (isDirect){
                        navigation?.newRootFragment(HomeFragment.newInstance())
                    } else {
                        navigation?.back()
                    }
                }
                else -> {}
            }
        })
    }
    private fun events() {
        btnConfirm.setOnClickListener {
            showProgress()
            loginViewModel.loginWithEmailAndPassword(etEmail.text.toString(), etPassword.text.toString()
            ) {
                hideProgress()
                displayError(it)
            }
        }

        etEmail?.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                tvError?.isVisible?.then { tvError?.invisible() }
            }
        })
        etPassword.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                imgEye.visibility = if (etPassword.length() > 0) View.VISIBLE else View.GONE
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                tvError?.isVisible?.then { tvError?.invisible() }
            }

        })
        imgEye?.setOnTouchListener { _, motionEvent ->
            val current = etPassword.selectionStart
            when (motionEvent.action) {
                MotionEvent.ACTION_DOWN -> {
                    // Hold
                    etPassword.inputType = InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                    etPassword.typeface = etPassword.typeface
                    etPassword.setSelection(current)
                    return@setOnTouchListener true
                }
                MotionEvent.ACTION_UP -> {
                    //Release
                    etPassword.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                    etPassword.typeface = etPassword.typeface
                    etPassword.setSelection(current)
                    return@setOnTouchListener true
                }
            }
            false
        }

        tvRegister?.setOnClickListener { navigation?.addFragment(RegisterScreen.newInstance()) }
    }


    private fun displayError(errorMsg: String) {
        tvError?.visible()
        tvError.text = errorMsg
    }
}
