package com.anhtam.domain.v2.wrap

import com.anhtam.domain.v2.Gamev2
import com.anhtam.domain.v2.Post
import com.squareup.moshi.Json

class WrapThaoLuan(
        @Json(name = "games")
        var game: Gamev2?,
        @Json(name = "forumList")
        var mList: List<Post>?
)