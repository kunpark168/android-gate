package com.anhtam.gate9.v2.search

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import javax.inject.Inject

class SearchViewModel @Inject constructor(): ViewModel() {
    val mKey = MutableLiveData<String>()
}