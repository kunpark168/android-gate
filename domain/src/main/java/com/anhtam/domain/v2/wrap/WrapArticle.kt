package com.anhtam.domain.v2.wrap

import com.anhtam.domain.v2.Article
import com.squareup.moshi.Json

data class WrapArticle(
        @Json(name = "video")
        var mVideo: List<Article>? = null,
        @Json(name = "gallery")
        var mGallery: List<Article>? = null,
        @Json(name = "manual")
        var mManual: List<Article>? = null,
        @Json(name = "games")
        var mGames: List<Article>? = null
)