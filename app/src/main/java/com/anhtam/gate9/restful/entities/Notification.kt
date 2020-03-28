package com.anhtam.gate9.restful.entities

import com.anhtam.domain.v2.Userv1
import com.squareup.moshi.Json
import java.util.*

class Notification(
        @Json(name = "commentId") var commentId: Int? = null,
        @Json(name = "title") var title: String? = null,
        @Json(name = "content") var content: String? = null,
        @Json(name = "url") var url: String? = null,
        @Json(name = "createdDate") var createdDate: String? = null,
        @Json(name = "createdUser") var createdUser: Userv1? = null
)