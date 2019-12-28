package com.anhtam.domain

import com.anhtam.domain.dto.Review
import com.anhtam.domain.dto.Reaction
import com.squareup.moshi.Json

data class ReviewDTO(
        @Json(name = "comment_id") override var commentId: String?,
        @Json(name = "avatar") override var avatar: String?,
        @Json(name = "name") override var name: String?,
        @Json(name = "user_id") override var userId: String?,
        @Json(name = "link") override var link: String?,
        @Json(name = "content") override var content: String?,
        @Json(name = "photo") override var photo: String?,
        @Json(name = "like") override var like: String?,
        @Json(name = "rating") override var rating: Float?,
        @Json(name = "list_like") override var reaction: Reaction?,
        @Json(name = "created_date") override var createdDate: String?
) : Review