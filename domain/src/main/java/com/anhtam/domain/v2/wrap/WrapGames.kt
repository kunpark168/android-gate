package com.anhtam.domain.v2.wrap

import com.anhtam.domain.v2.Gamev1
import com.squareup.moshi.Json

class WrapGames(
        @Json(name = "games")
        override var wrap: List<Gamev1>
) : WrapBase<List<Gamev1>>