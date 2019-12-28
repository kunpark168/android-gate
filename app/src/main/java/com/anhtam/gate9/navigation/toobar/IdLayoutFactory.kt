package com.anhtam.gate9.navigation.toobar

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes

class IdLayoutFactory(@LayoutRes private val layoutId: Int) : LayoutFactory {
    override fun produceLayout(inflater: LayoutInflater, container: ViewGroup?): View {
        return inflater.inflate(layoutId, container, false)
    }

}