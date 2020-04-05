package com.anhtam.domain.v2

import com.squareup.moshi.Json

open class Article(
        @Json(name = "article_id")
        var mArticleId: Int? = null,
        @Json(name = "title")
        var mTitle: String? = null,
        @Json(name = "sapo")
        var mSapo: String? = null,
        @Json(name = "avatar")
        var mAvatar: String? = null,
        @Json(name = "created_time")
        var mCreatedTime: String? = null,
        @Json(name = "created_date")
        var mCreatedDate: String? = null,
        @Json(name = "domain")
        var mDomain: String? = null,
        @Json(name = "category_name")
        var mCategoryName: String? = null,
        @Json(name = "photos")
        var mPhotos: String? = null,
        @Json(name = "category_id")
        var mCategoryId: String? = null,
        @Json(name = "link")
        var mLink: String? = null,
        @Json(name = "number_comment")
        var mNumberComment: Int? = null,
        @Json(name = "game")
        var mGame: Gamev1? = null,
        @Json(name = "created_user")
        var mUser: Userv1? = null,
        @Json(name = "content")
        var mContent: String? = null,
        @Json(name = "category")
        var mCategory: ArticleCategory? = null,
        @Json(name = "article_type")
        var mArticleType: Int? = null,
        @Json(name = "comment_list")
        var mComments: List<Post>? = null
)

data class ArticleCategory(
        @Json(name = "categoryId")
        var mCategoryId: Int? = null,
        @Json(name = "categoryName")
        var mCategoryName: String? = null,
        @Json(name = "categoryPath")
        var mCategoryPath: String? = null
)