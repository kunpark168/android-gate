package com.anhtam.domain
import com.squareup.moshi.Json


data class GameDTO(
    @Json(name = "game_id")
    var gameId: String? = null,
    @Json(name = "name")
    var name: String? = null,
    @Json(name = "avatar")
    var avatar: String? = null,
    @Json(name = "theme")
    var theme: String? = null,
    @Json(name = "gameType")
    var gameType: List<Type>? = null,
    @Json(name = "capacity")
    var capacity: String? = null,
    @Json(name = "point")
    var point: String? = null,
    @Json(name = "total_vote")
    var totalVote: Int? = null,
    @Json(name = "link")
    var link: String? = null,
    @Json(name = "user_download")
    var userDownload: String? = null
) : Base()