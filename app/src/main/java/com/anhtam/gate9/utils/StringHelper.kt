package com.anhtam.gate9.utils

import com.anhtam.gate9.config.Config

fun String.toImage() = if(this.startsWith("http")) this else Config.IMG_URL + this
