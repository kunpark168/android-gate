package com.anhtam.domain.v2.wrap

import com.anhtam.domain.v2.PostEntity
import com.squareup.moshi.Json

class WrapListing(
        @Json(name = "listing")
        override var wrap: List<PostEntity>
) : WrapBase<List<PostEntity>>