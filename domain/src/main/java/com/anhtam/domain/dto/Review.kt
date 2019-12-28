package com.anhtam.domain.dto

import com.squareup.moshi.Json

interface Review {
    var commentId: String?
    var avatar: String?
    var name: String?
    var userId: String?
    var link: String?
    var content: String?
    var photo: String?
    var like: String?
    var rating: Float?
    var reaction: Reaction?
    var createdDate: String?
}

data class Reaction(
        @Json(name = "total_like") var totalLike: Long?,
        @Json(name = "total_dislike") var totalDislike: Long?
)