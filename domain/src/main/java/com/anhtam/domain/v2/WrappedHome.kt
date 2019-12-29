package com.anhtam.domain.v2

import com.anhtam.domain.Banner
import com.anhtam.domain.Base
import com.anhtam.domain.Game
import com.squareup.moshi.Json

data class WrappedHome(
        @Json(name = "listing") val mListing: List<PostEntity>? = null,
        @Json(name = "banner") val mBanner: List<Banner>? = null,
        @Json(name = "new_game") val mGames: List<Game>? = null
): Base()