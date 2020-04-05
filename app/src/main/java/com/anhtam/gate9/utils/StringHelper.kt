package com.anhtam.gate9.utils

import android.content.Context
import com.anhtam.gate9.R
import com.anhtam.gate9.config.Config
import com.anhtam.gate9.vo.IllegalReturn

fun String.toImage() = if (this.startsWith("http")) this else Config.IMG_URL + this

fun String.convertInt() = try {
    this.toInt()
} catch (e: NumberFormatException) {
    throw IllegalReturn()
}


fun Context.checkValidationRegistration(email: String?, password: String?, confirmPassword: String?, displayName: String?): String? {
    if (email.isNullOrEmpty())
        return applicationContext.resources.getString(R.string.required_email_register)
    if (password.isNullOrEmpty())
        return applicationContext.resources.getString(R.string.required_password_register)
    if (confirmPassword.isNullOrEmpty())
        return applicationContext.resources.getString(R.string.required_confirm_password_register)
    if (displayName.isNullOrEmpty())
        return applicationContext.resources.getString(R.string.required_display_name_register)
    if (password != confirmPassword)
        return applicationContext.resources.getString(R.string.password_register_not_match)
    if (!Validation.validationEmail(email))
        return applicationContext.resources.getString(R.string.email_invalid)
    return null
}
