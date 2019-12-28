package com.anhtam.gate9.ui.base

import androidx.lifecycle.ViewModel
import java.lang.ref.WeakReference

abstract class BaseViewModel<N>: ViewModel() {
    private lateinit var mNavigator: WeakReference<N>

    fun getNavigator(): N? {
        return mNavigator.get()
    }

    fun setNavigator(navigator: N) {
        mNavigator = WeakReference(navigator)
    }
}