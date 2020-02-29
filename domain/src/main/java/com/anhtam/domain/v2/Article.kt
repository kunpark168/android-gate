package com.anhtam.domain.v2
import com.squareup.moshi.Json

open class Article(
    @Json(name = "article_id")
    var mArticleId: Int? = null,
    @Json(name = "articleId")
    var mId: Int? = null,
    @Json(name = "title")
    var mTitle: String? = null,
    @Json(name = "sapo")
    var mSapo: String? = null,
    @Json(name = "avatar")
    var mAvatar: String? = null,
    @Json(name = "created_time")
    var mCreatedTime: String? = null,
    @Json(name = "domain")
    var mDomain: String? = null,
    @Json(name = "category_name")
    var mCategoryName: String? = null,
    @Json(name = "category_id")
    var mCategoryId: String? = null,
    @Json(name = "link")
    var mLink: String? = null,
    @Json(name = "number_comment")
    var mNumberComment: Int? = null,
    @Json(name = "game")
    var mGame: Gamev1? = null,
    @Json(name = "createdUser")
    var mUser: Userv2? = null
)