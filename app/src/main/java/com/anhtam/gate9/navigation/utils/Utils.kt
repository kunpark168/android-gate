package com.anhtam.gate9.navigation.utils

import android.content.Context
import android.util.TypedValue
import android.util.TypedValue.COMPLEX_UNIT_DIP

object Utils {

    fun dp(context: Context, dp: Float) : Float {
        return applyDimen(context, dp, COMPLEX_UNIT_DIP)
    }

    private fun applyDimen(context: Context, value: Float, unit: Int): Float {
        val res = context.resources
        return TypedValue.applyDimension(unit, value, res.displayMetrics)
    }
}