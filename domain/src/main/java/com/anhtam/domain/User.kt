package com.anhtam.domain

import com.squareup.moshi.Json


data class User(
        @Json(name = "created_user_id")
        val createdUserId: String?,
        @Json(name = "user_name")
        val userName: String?,
        @Json(name = "avatar")
        val avatar: String?,
        @Json(name = "following")
        val following: Long?,
        @Json(name = "follower")
        val follower: Long?,
        @Json(name = "name")
        val name: String?,
        @Json(name = "user_id")
        val user_id: String?,
        @Json(name = "follow")
        val follow: String?,
        @Json(name = "avatar_path")
        val avatar_path: String?,
        @Json(name = "theme")
        val theme: String?,
        @Json(name = "point")
        val point: Long?,
        @Json(name = "birth")
        val birth: String?,
        @Json(name = "address")
        val address: String?,
        @Json(name = "note")
        val note: String?,
        @Json(name = "cmt")
        val cmt: String?,
        @Json(name = "gender")
        val gender: String?,
        @Json(name = "medal")
        val medal: String?,
        @Json(name = "url")
        val url: String?,
        @Json(name = "facebook")
        val facebook: String?,
        @Json(name = "email")
        val email: String?,
        @Json(name = "phone")
        val phone: String?,
        @Json(name = "skype")
        val skype: String?,
        @Json(name = "qq")
        val qq: String?,
        @Json(name = "total_post")
        val totalPost: String?,
        @Json(name = "total_comment_post")
        val totalCommentPost: String?,
        @Json(name = "total_reply")
        val totalReply: Long?,
        @Json(name = "created_date")
        val createdDate: String?,
        @Json(name = "total_data")
        val totalData: TotalData?,
        @Json(name = "total_game")
        val totalGame: TotalGame?
)

data class TotalData(
    @Json(name = "total_news")
    val totalNews: String?,
    @Json(name = "total_manual")
    val totalManual: String?,
    @Json(name = "total_video")
    val totalVideo: String?,
    @Json(name = "total_gallery")
    val totalGallery: String?
)

data class TotalGame(
    @Json(name = "totalgameopen")
    val totalgameopen: String?,
    @Json(name = "totalgamebeta")
    val totalgamebeta: Int?,
    @Json(name = "totalgamecoming")
    val totalgamecoming: Int?,
    @Json(name = "totalgameclosed")
    val totalgameclosed: String?
)