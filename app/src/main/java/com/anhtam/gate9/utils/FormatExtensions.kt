package com.anhtam.gate9.utils

import android.content.Context
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import com.anhtam.gate9.R


fun Context.getStringColor(@ColorRes color: Int): String {
    return "#" + Integer.toHexString(ContextCompat.getColor(this, color) and 0x00ffffff)
}

fun String.htmlColor(context: Context, @ColorRes color: Int): String {
    val colorString = context.getStringColor(color)
    return "<font color='".plus(colorString).plus("'>").plus(this).plus("</font>")
}

fun String.userStyle(context: Context): String {
    return "<b>".plus(this.htmlColor(context, R.color.notificationAvatarColor)).plus("</b>")
}

fun String.targetStyle(context: Context): String {
    return this.htmlColor(context, R.color.colorIndicatorNotification)
}

fun Context.getColorCompat(colorId: Int): Int {
    return ContextCompat.getColor(this, colorId)
}

fun Double.format(digits: Int) = "%.${digits}f".format(this)