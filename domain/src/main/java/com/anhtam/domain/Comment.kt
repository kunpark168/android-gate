package com.anhtam.domain

import com.squareup.moshi.Json

data class Comment(
    @Json(name = "comment_id")
    val commentId: String?,
    @Json(name = "avatar")
    val avatar: String?,
    @Json(name = "name")
    val name: String?,
    @Json(name = "user_id")
    val userId: String?,
    @Json(name = "link")
    val link: String?,
    @Json(name = "content")
    val content: String?,
    @Json(name = "photo")
    val photo: Any?,
    @Json(name = "like")
    val like: String?,
    @Json(name = "created_date")
    val createdDate: String?,
    @Json(name = "parent")
    val parent: CommentList?
)

data class CommentList(
    @Json(name = "total")
    val total: Int?,
    @Json(name = "comments")
    val comments: List<Comment>?
)