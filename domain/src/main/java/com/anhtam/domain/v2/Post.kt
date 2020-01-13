package com.anhtam.domain.v2
import com.anhtam.domain.Base
import com.squareup.moshi.Json


data class Post(
    @Json(name = "comment_id")
    var commentId: Long? = null,
    @Json(name = "user")
    var user: Userv2? = null,
    @Json(name = "content")
    var content: String? = null,
    @Json(name = "photo")
    var photo: String? = null,
    @Json(name = "like")
    var like: String? = null,
    @Json(name = "created_date")
    var createdDate: String? = null,
    @Json(name = "title")
    var title: String? = null,
    @Json(name = "total_like")
    var totalLike: String = "0",
    @Json(name = "total_dislike")
    var totalDislike: String = "0",
    @Json(name = "total_reply")
    var totalReply: String = "0",
    @Json(name = "total_love")
    var totalLove: String = "0",
    @Json(name = "point")
    var point: String = "0",
    @Json(name = "game")
    var game: Gamev1? = null,
    @Json(name = "child")
    var child: List<Post>? = null
) : Base()