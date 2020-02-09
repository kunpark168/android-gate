package com.anhtam.domain.v2
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
    @Json(name = "created_user_id")
    var mCreatedUserId: Userv1? = null,
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
    var mNphid: String? = null
)