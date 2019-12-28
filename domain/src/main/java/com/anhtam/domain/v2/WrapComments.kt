package com.anhtam.domain.v2

import com.squareup.moshi.Json

data class WrapComments(
        @Json(name = "comments")
        var mComments: List<PostEntity>? = null
)