package com.anhtam.domain

import com.squareup.moshi.Json

data class Banner(
        @Json(name ="picture") val picture: String?,
        @Json(name = "url") val url: String?
)