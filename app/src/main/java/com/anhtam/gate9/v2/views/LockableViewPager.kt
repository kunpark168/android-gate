package com.anhtam.gate9.v2.views

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent

import androidx.viewpager.widget.ViewPager

class LockableViewPager @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null)
    : ViewPager(context, attrs) {
    override fun onTouchEvent(event: MotionEvent) = false
    override fun onInterceptTouchEvent(event: MotionEvent) = false
}
