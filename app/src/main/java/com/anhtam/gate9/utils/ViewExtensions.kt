package com.anhtam.gate9.utils

import android.os.Build
import android.text.Html
import android.text.SpannableString
import android.view.MotionEvent
import android.view.View
import android.widget.TextView
import androidx.lifecycle.MutableLiveData
import com.jakewharton.rxbinding.view.RxView
import java.util.concurrent.TimeUnit

/**
 * Check 2 vars NOT null
 */

fun <T1, T2> ifNotNull(value1: T1?, value2: T2?, bothNotNull: (T1, T2) -> (Unit)) {
    if (value1 != null && value2 != null) {
        bothNotNull(value1, value2)
    }
}


fun checkValueString(str: String?): String {
    return str ?: "-"
}

/*
 * LiveData Helper
 * Set value
 */

fun <T> MutableLiveData<T>.setValueDiff(data: T) {
    if (this.value != data) {
        this.value = data
    }
}

/*
 * UX for click
 */
fun View.customOnClickHolder(callback: () -> Unit) {
    this.setOnTouchListener { _, event ->
        when(event.action) {
            MotionEvent.ACTION_DOWN -> alpha = 0.5f
            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> alpha = 1.0f
            else -> {}
        }
        false
    }
    setOnClickListener{
        callback()
    }
}

fun View.debounceClick(callback: () -> Unit) {
    this.setOnTouchListener { _, event ->
        when(event.action) {
            MotionEvent.ACTION_DOWN -> alpha = 0.5f
            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> alpha = 1.0f
            else -> {}
        }
        false
    }

    RxView.clicks(this)
            .throttleFirst(500, TimeUnit.MILLISECONDS)
            .subscribe {
                 callback()
            }
}

fun View.debounceClickWithoutHolder(callback: () -> Unit) {
    RxView.clicks(this)
            .throttleFirst(500, TimeUnit.MILLISECONDS)
            .subscribe {
                callback()
            }
}

/*
 * Color text view multi
 */
fun TextView.setHtml(html: String?) {
    text = if (html == null) {
        SpannableString("")
    }
    else {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Html.fromHtml(html, Html.FROM_HTML_MODE_LEGACY)
        } else {
            Html.fromHtml(html)
        }
    }
}

