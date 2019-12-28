package com.anhtam.gate9.navigation.toobar

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

interface LayoutFactory {
    fun produceLayout(inflater: LayoutInflater, container: ViewGroup?): View
}