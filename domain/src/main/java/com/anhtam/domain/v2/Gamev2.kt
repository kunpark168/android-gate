package com.anhtam.domain.v2

import com.anhtam.domain.v2.protocol.Game
import com.squareup.moshi.Json

class Gamev2(
        @Json(name = "gameId")
        override var gameId: Int? = null,
        @Json(name = "name")
        override var name: String? = null,
        @Json(name = "avatar")
        override var avatar: String? = null,
        @Json(name = "background")
        override var background: String? = null,
        @Json(name = "description")
        override var description: String? = null,
        @Json(name = "point")
        override var point: String? = null,
        @Json(name = "created_date")
        override var createdDate: String? = null,
        @Json(name = "created_user_id")
        override var createdUserId: Int? = null,
        @Json(name = "he_dieu_hanh")
        override var heDieuHanh: String? = null,
        @Json(name = "num_viewed")
        override var numViewed: String? = null,
        @Json(name = "num_downloaded")
        override var numDownloaded: String? = null,
        @Json(name = "num_liked")
        override var numLiked: String? = null,
        @Json(name = "num_disliked")
        override var numDisliked: String? = null,
        @Json(name = "capacity")
        override var capacity: String? = null,
        @Json(name = "gameTypes")
        override var gameTypes: List<Type>? = null,
        @Json(name = "gameType")
        override var gameType: Type? = null,
        @Json(name = "game_types")
        override var gameTypeStr: String? = null,
        @Json(name = "fan_page")
        override var fanPage: String? = null,
        @Json(name = "homepage")
        override var homepage: String? = null,
        @Json(name = "photos")
        override var photos: String? = null,
        @Json(name = "numFollower")
        override var follower: Int? = null,
        @Json(name = "numFollowing")
        override var following: Int? = null,
        @Json(name = "follow")
        override var follow: Boolean? = null,
        @Json(name = "post")
        override var post: Int? = null,
        @Json(name = "imgCover")
        override var imgCover: String? = null,
        @Json(name = "country")
        override var country: String? = null,
        @Json(name = "email")
        override var email: String? = null,
        @Json(name = "phone")
        override var phone: String? = null,
        @Json(name = "group")
        override var group: String? = null,
        @Json(name = "rating")
        override var mRating: Double? = null,
        @Json(name = "numRate")
        override var mNumRating: Int? = null,
        @Json(name = "userNPH")
        override var mNPH: Userv2? = null,
        @Json(name = "status")
        override var mStatus: Int? = null,
        @Json(name = "state")
        override var mState: Int? = null
): Game