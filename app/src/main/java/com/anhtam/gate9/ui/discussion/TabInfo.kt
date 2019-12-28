package com.anhtam.gate9.ui.discussion

import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class TabInfo(
        @DrawableRes val icon: Int,
        @ColorRes val color: Int,
        @StringRes val title: Int
)