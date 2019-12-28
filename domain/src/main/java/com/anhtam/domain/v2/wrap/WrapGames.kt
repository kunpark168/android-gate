package com.anhtam.domain.v2.wrap

import com.anhtam.domain.v2.GameEntity
import com.squareup.moshi.Json

class WrapGames(
        @Json(name = "games")
        override var wrap: List<GameEntity>
) : WrapBase<List<GameEntity>>