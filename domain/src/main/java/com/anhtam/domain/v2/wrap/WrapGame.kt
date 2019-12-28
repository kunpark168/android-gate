package com.anhtam.domain.v2.wrap

import com.anhtam.domain.v2.GameEntity
import com.squareup.moshi.Json

class WrapGame(
        @Json(name = "game")
        override var wrap: GameEntity
) : WrapBase<GameEntity>