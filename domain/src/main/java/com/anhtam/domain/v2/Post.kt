package com.anhtam.domain.v2
import com.anhtam.domain.Base
import com.squareup.moshi.Json
import java.util.*


data class PostEntity(
    @Json(name = "comment_id")
    var commentId: Long? = null,
    @Json(name = "user")
    var user: User? = null,
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
    @Json(name = "game")
    var game: Game? = null,
    @Json(name = "child")
    var child: List<PostEntity>? = null
) : Base()

data class Game(
    @Json(name = "game_id")
    var gameId: String? = null,
    @Json(name = "name")
    var name: String? = null,
    @Json(name = "avatar")
    var avatar: String? = null,
    @Json(name = "background")
    var background: String? = null,
    @Json(name = "follower")
    var follower: String? = "0",
    @Json(name = "follow")
    var follow: String? = null,
    @Json(name = "post")
    var post: Int = 0
) : Base()