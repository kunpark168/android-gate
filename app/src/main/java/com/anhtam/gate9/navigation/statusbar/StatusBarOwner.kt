package com.anhtam.gate9.navigation.statusbar

import android.app.Activity
import android.os.Build
import android.view.View.*
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent

interface StatusBarOwner {
    val statusBarHeight: Int
    val isStatusBarTranslucent: Boolean
    var isStatusBarLight: Boolean
}

interface StatusBarProvider {
    fun provideStatusBarOwner(): StatusBarOwner
}

class StatusBarManager(
    private val activity: Activity,
    private val isThemeTranslucent: Boolean
) : StatusBarOwner, LifecycleObserver {

    override val statusBarHeight: Int by lazy { computeStatusBarHeight() }
    override val isStatusBarTranslucent: Boolean by lazy { isThemeTranslucent && statusBarHeight > 0 }
    override var isStatusBarLight: Boolean
        get() = if (isMarshallow()) {
            with(activity.window.decorView) {
                systemUiVisibility and SYSTEM_UI_FLAG_LIGHT_STATUS_BAR == systemUiVisibility
            }
        } else {
            false
        }
        set(value) {
            if (isMarshallow()) {
                activity.window.decorView.run {
                    systemUiVisibility = if (value) {
                        systemUiVisibility or SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
                    } else {
                        systemUiVisibility and SYSTEM_UI_FLAG_LIGHT_STATUS_BAR.inv()
                    }

                }
            }
        }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun prepare() {
        if (isLollipop() && isStatusBarTranslucent) {
            activity.window.decorView.systemUiVisibility =
                    SYSTEM_UI_FLAG_LAYOUT_STABLE or SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        }
    }

    private fun isLollipop(): Boolean {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP
    }

    private fun isMarshallow(): Boolean {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
    }

    private fun computeStatusBarHeight(): Int {
        return if (isLollipop()) {
            var result = 0
            val resourceId = activity.resources.getIdentifier("status_bar_height", "dimen", "android")
            if (resourceId > 0) {
                result = activity.resources.getDimensionPixelSize(resourceId)
            }
            result
        } else {
            0
        }
    }
}