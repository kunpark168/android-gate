package com.anhtam.domain.v2

import com.squareup.moshi.Json

data class Type(
        @Json(name = "name")
        val name: String? = null
)