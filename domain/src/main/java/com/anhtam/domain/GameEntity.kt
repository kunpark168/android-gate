package com.anhtam.domain

import androidx.annotation.DrawableRes
import com.anhtam.domain.dto.Game

data class GameEntity(

        override val mId: String,
        override val mTitle: String?,
        @DrawableRes
        override val mBanner: Int?
) : Game