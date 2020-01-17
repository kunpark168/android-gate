package com.anhtam.gate9.v2.shared.keyboard

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class BottomKeyboardViewModel : ViewModel(){
    internal val mState = MutableLiveData<AbstractBottomKeyboardFragment.State>()
}