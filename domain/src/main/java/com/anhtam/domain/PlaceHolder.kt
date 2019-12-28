package com.anhtam.domain
import com.squareup.moshi.Json


data class PlaceHolder(
    @Json(name = "albumId")
    var albumId: Int? = null,
    @Json(name = "id")
    var id: Int? = null,
    @Json(name = "title")
    var title: String? = null,
    @Json(name = "url")
    var url: String? = null,
    @Json(name = "thumbnailUrl")
    var thumbnailUrl: String? = null
)