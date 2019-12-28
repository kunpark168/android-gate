package com.anhtam.domain.v2
import com.squareup.moshi.Json


data class DataEntity(
    @Json(name = "game_id")
    var gameId: Int? = null,
    @Json(name = "name")
    var name: String? = null,
    @Json(name = "avatar_game")
    var avatarGame: String? = null,
    @Json(name = "link")
    var link: String? = null,
    @Json(name = "apple_store")
    var appleStore: String? = null,
    @Json(name = "google_play")
    var googlePlay: String? = null
)