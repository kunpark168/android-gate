package com.anhtam.domain

import com.squareup.moshi.Json

data class WrappedUser(
        @Json(name = "user")
        val user: User?
): Base()