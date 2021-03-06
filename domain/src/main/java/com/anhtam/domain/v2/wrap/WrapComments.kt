package com.anhtam.domain.v2.wrap

import com.anhtam.domain.v2.Post
import com.squareup.moshi.Json

data class WrapComments(
        @Json(name = "detail")
        var mDetails: Post? = null,
        @Json(name = "comments")
        var mComments: List<Post>? = null
)