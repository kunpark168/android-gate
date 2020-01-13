package com.anhtam.gate9.v2.auth.login

import androidx.lifecycle.ViewModel
import com.anhtam.gate9.session.AuthCallBack
import com.anhtam.gate9.session.AuthClient
import com.anhtam.gate9.session.AuthResource
import com.anhtam.gate9.session.SessionManager
import com.anhtam.gate9.storage.StorageManager
import timber.log.Timber
import javax.inject.Inject


class LoginViewModel @Inject constructor(
        private val mSessionManager: SessionManager) : ViewModel() {

    fun loginWithEmailAndPassword(email: String, password: String, errorLogin: (String)->Unit) {
        /* Local validate */
        if(!validate(email, password, errorLogin)) return

        /* Send request */
        mSessionManager.authenticatedWithEmail(email, password)
    }

    private fun validate(email: String, password: String, errorLogin: (String) -> Unit): Boolean {
        return when {
            email.isEmpty() -> {
                errorLogin("Email is empty")
                false
            }
            password.isEmpty() -> {
                errorLogin("Password is empty")
                false
            }
            else -> true
        }
    }
}