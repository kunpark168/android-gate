package com.anhtam.gate9.helper

import android.text.Html


fun String.formatToHtml() = Html.fromHtml(this).toString()

class FormatHelper {
    companion object {

    }
}