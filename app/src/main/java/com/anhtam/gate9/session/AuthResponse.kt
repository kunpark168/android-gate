package com.anhtam.gate9.session

import com.anhtam.domain.User
import com.squareup.moshi.Json
import java.io.Serializable

data class AuthResponse(
        @Json(name = "status") val status: String?,
        @Json(name = "message") val message: String?,
        @Json(name = "access_token") val token: String?,
        @Json(name = "user") val user: User?
): Serializable