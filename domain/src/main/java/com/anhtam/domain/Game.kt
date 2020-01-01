package com.anhtam.domain

import com.squareup.moshi.Json

data class Game(
        @Json(name = "game_id")
    val gameId: String?,
        @Json(name = "name")
    val name: String?,
        @Json(name = "avatar")
        val avatar: String?,
        @Json(name = "avatar_game")
        val avatarGame: String?,
        @Json(name = "background")
    val background: String?,
        @Json(name = "type")
    val types: List<Type>?,
        @Json(name = "gameTypes")
        val gameType: List<Type>?,
        @Json(name = "publisher")
    val publisher: Publisher?,
        @Json(name = "country")
    val country: Country?,
        @Json(name = "released_date")
    val releasedDate: String?,
        @Json(name = "status")
    val status: String?,
        @Json(name = "content")
    val content: String?,
        @Json(name = "contact_info")
    val contactInfo: ContactInfo?,
        @Json(name = "cover")
    val cover: String?,
        @Json(name = "follow")
    val follow: String?,
        @Json(name = "follower")
    val follower: Long?,
        @Json(name = "link")
    val link: String?,
        @Json(name = "total_post")
    val totalPost: Long?,
        @Json(name = "post")
    val post: Long?,
        @Json(name = "rating")
    val rating: Float?,
        @Json(name = "vote")
    val vote: Vote?
        /*
            @Json(name = "user_vote")
            val userVote: List<Any>?
           */
): Base()

data class ContactInfo(
    @Json(name = "email")
    val email: String?,
    @Json(name = "fanpage")
    val fanpage: String?,
    @Json(name = "group")
    val group: String?,
    @Json(name = "homepage")
    val homepage: String?,
    @Json(name = "tel")
    val tel: String?
): Base()

data class Country(
    @Json(name = "cat_id")
    val catId: String?,
    @Json(name = "name")
    val name: String?
): Base()

data class Publisher(
    @Json(name = "name")
    val name: String?,
    @Json(name = "user_id")
    val userId: String?
): Base()

data class Type(
    @Json(name = "name")
    val name: String?,
    @Json(name = "slug")
    val slug: String?,
    @Json(name = "type_id")
    val typeId: String?
): Base()

data class Vote(
    @Json(name = "1star")
    val star01: Long?,
    @Json(name = "2star")
    val star02: Long?,
    @Json(name = "3star")
    val star03: Long?,
    @Json(name = "4star")
    val star04: Long?,
    @Json(name = "5star")
    val star05: Long?,
    @Json(name = "total_vote")
    val totalVote: Long?
): Base()