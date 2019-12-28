package com.anhtam.domain
import com.squareup.moshi.Json


data class Article(
        @Json(name = "article_id")
    val mArticleId: String? = null,
        @Json(name = "avatar")
    val mAvatar: String? = null,
        @Json(name = "title")
    val mTitle: String? = null,
        @Json(name = "sapo")
    val mSapo: String? = null,
        @Json(name = "created_date")
    val mCreatedDate: String? = null,
        @Json(name = "category")
    val mCategory: Category? = null,
        @Json(name = "game")
    val mGame: Game? = null
) : Base()

data class Category(
    @Json(name = "category_id")
    val mCategoryId: String? = null,
    @Json(name = "name")
    val mName: String? = null
)
