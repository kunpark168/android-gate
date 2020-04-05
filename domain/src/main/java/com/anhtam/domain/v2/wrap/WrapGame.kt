package com.anhtam.domain.v2.wrap

import com.anhtam.domain.v2.Gamev1
import com.anhtam.domain.v2.Gamev2
import com.squareup.moshi.Json

class WrapGame(
        @Json(name = "game")
        override var wrap: Gamev2
) : WrapBase<Gamev2>