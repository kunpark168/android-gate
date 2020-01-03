package com.anhtam.gate9.utils

import com.anhtam.gate9.config.Config
import com.anhtam.gate9.vo.IllegalReturn

fun String.toImage() = if(this.startsWith("http")) this else Config.IMG_URL + this

fun String.convertInt() = try {
    this.toInt()
} catch (e: NumberFormatException) {
    throw IllegalReturn()
}
