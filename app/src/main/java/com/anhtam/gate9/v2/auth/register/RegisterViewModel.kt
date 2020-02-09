package com.anhtam.gate9.v2.auth.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.anhtam.domain.Base
import com.anhtam.gate9.repository.SocialRepository
import of.bum.network.helper.Resource
import javax.inject.Inject

class RegisterViewModel @Inject constructor(val repository: SocialRepository): ViewModel(){

    companion object{
        private val rules: List<RegistrationRule> = arrayListOf(
                EmailValidationRule(), PasswordValidationRule()
        )
    }

    fun validate(email: String, password: String, name: String): Boolean{
        // validate email
        // validate password
        // validate name
        val data = RegistrationData(email, password, name)
        rules.forEach { rule ->
            val isPassed = rule.validate(data)
            if (!isPassed) return false
        }
        return true
    }

    fun registerWithEmail(email: String, password: String, name: String): LiveData<Resource<Base>>{
        return repository.registerWithEmail(email, password, name)
    }
}