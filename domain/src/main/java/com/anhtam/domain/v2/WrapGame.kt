package com.anhtam.domain.v2

import com.squareup.moshi.Json

data class WrapGame(
        @Json(name = "games")
        var mGames: List<GameEntity>? = null
)