package com.anhtam.domain.v2
import com.anhtam.domain.Base
import com.squareup.moshi.Json


data class GameEntity(
    @Json(name = "game_id")
    var gameId: Int? = null,
    @Json(name = "name")
    var name: String? = null,
    @Json(name = "avatar")
    var avatar: String? = null,
    @Json(name = "description")
    var description: String? = null,
    @Json(name = "point")
    var point: String? = null,
    @Json(name = "created_date")
    var createdDate: String? = null,
    @Json(name = "created_user_id")
    var createdUserId: Int? = null,
    @Json(name = "he_dieu_hanh")
    var heDieuHanh: String? = null,
    @Json(name = "num_viewed")
    var numViewed: String? = null,
    @Json(name = "num_downloaded")
    var numDownloaded: String? = null,
    @Json(name = "num_liked")
    var numLiked: String? = null,
    @Json(name = "num_disliked")
    var numDisliked: String? = null,
    @Json(name = "capacity")
    var capacity: String? = null,
    @Json(name = "game_types")
    var gameTypes: String? = null,
    @Json(name = "fan_page")
    var fanPage: String? = null,
    @Json(name = "homepage")
    var homepage: String? = null,
    @Json(name = "photos")
    var photos: String? = null,
    @Json(name = "follower")
    var follower: String? = null,
    @Json(name = "following")
    var follow: String? = null,
    @Json(name = "post")
    var post: String? = null
) : Base()