package com.anhtam.gate9.v2.shared.views

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class VisibleViewModel : ViewModel(){

    val mIsUIVisible = MutableLiveData<Boolean>(false)
}