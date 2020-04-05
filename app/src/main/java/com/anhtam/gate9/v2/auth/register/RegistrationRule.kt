package com.anhtam.gate9.v2.auth.register

import com.anhtam.gate9.utils.Validation

interface RegistrationRule {
    fun validate(data: RegistrationData): Boolean
}

data class RegistrationData(
        val email: String,
        val password: String,
        val name: String
)

class EmailValidationRule: RegistrationRule {
    override fun validate(data: RegistrationData): Boolean {
        return Validation.validationEmail(data.email)
    }
}

class PasswordValidationRule: RegistrationRule {
    override fun validate(data: RegistrationData): Boolean {
        return !data.password.isNullOrEmpty()
    }
}

