package com.anhtam.gate9.session

interface AuthCallBack {
    fun onAuthorized()
    fun onUnauthorized(message: String)
}