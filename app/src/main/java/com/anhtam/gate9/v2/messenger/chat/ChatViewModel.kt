package com.anhtam.gate9.v2.messenger.chat

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import javax.inject.Inject

class ChatViewModel @Inject constructor() : ViewModel() {
    val userId = MutableLiveData<String>()
}