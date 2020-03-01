package com.anhtam.domain.v2.wrap

import com.anhtam.domain.v2.Article
import com.squareup.moshi.Json

data class WrapArticle(
        @Json(name = "detail")
        var mArticle: Article? = null,
        @Json(name = "concernList")
        var mConcernList: List<Article>? = null,
        @Json(name = "newList")
        var mNewList: List<Article>? = null
)