package com.anhtam.gate9.v2.ca_nhan

import androidx.lifecycle.ViewModel
import com.anhtam.gate9.v2.InfoService
import javax.inject.Inject

class UserViewModel @Inject constructor(val infoService: InfoService)
    : ViewModel() {

    fun getInfoUser() = infoService.getInfo()
}