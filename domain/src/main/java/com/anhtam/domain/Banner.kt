package com.anhtam.domain

import com.squareup.moshi.Json

data class Banner(
        @Json(name = "banner_id") val mId: Int?,
        @Json(name ="link") val link: String?,
        @Json(name = "url") val url: String?,
        @Json(name = "title") val title: String?
)