package com.anhtam.gate9.vo

import com.anhtam.domain.v2.Gamev1
import com.anhtam.domain.v2.Userv2
import com.chad.library.adapter.base.entity.MultiItemEntity
import com.squareup.moshi.Json


data class Rating(
        @Json(name = "comment_id")
        var mCommentId: Int? = null,
        @Json(name = "content")
        var mContent: String? = null,
        @Json(name = "link")
        var mLink: String? = null,
        @Json(name = "status")
        var mStatus: String? = null,
        @Json(name = "created_user")
        var mCreatedUser: Userv2? = null,
        @Json(name = "user")
        var mUser: Userv2? = null,
        @Json(name = "created_date")
        var mCreatedDate: String? = null,
        @Json(name = "modified_date")
        var mModifiedDate: String? = null,
        @Json(name = "parent_id")
        var mParentId: String? = null,
        @Json(name = "domain")
        var mDomain: String? = null,
        @Json(name = "title")
        var mTitle: String? = null,
        @Json(name = "sabo")
        var mSabo: String? = null,
        @Json(name = "photos")
        var mPhotos: String? = null,
        @Json(name = "num_liked")
        var mNumLiked: String? = null,
        @Json(name = "top")
        var mTop: String? = null,
        @Json(name = "game_id")
        var mGameId: String? = null,
        @Json(name = "num_dislike")
        var mNumDislike: String? = null,
        @Json(name = "num_favorite")
        var mNumFavorite: String? = null,
        @Json(name = "rating")
        var mRating: String? = null,
        @Json(name = "nphid")
        var mNphid: String? = null,
        @Json(name = "game")
        var mGame: Gamev1? = null
) : MultiItemEntity {
    override fun getItemType(): Int {
        return if (mCreatedUser != null && mGame == null) 1
        else 2
    }
}