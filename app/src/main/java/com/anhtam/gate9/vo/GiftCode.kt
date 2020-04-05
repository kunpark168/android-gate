package com.anhtam.gate9.vo

import com.anhtam.domain.v2.Gamev1
import com.anhtam.domain.v2.Post
import com.squareup.moshi.Json

data class GiftCode(
        @Json(name = "gift_code_id") var mId: Int?,
        @Json(name = "game_id") var mGame: Gamev1?,
        @Json(name = "title") var mTitle: String?,
        @Json(name = "link") var mLink: String?,
        @Json(name = "created_date") var mCreatedDate: String?,
        @Json(name = "content") var mContent: String?,
        @Json(name = "last_date") var mLastDate: String?,
        @Json(name = "type") var mType: String?,
        @Json(name = "rewrite") var mRewrite: String?,
        @Json(name = "picture") var mPicture: String?,
        @Json(name = "server") var mServer: String?,
        @Json(name = "note") var mNote: String?,
        @Json(name = "num_viewed") var mNumViewed: Long?,
        @Json(name = "gift_code_mes") var mGiftCodeMes: Int?,
        @Json(name = "published_date") var mPublishedDate: String?,
        @Json(name = "status") var mStatus: Int?,
        @Json(name = "total") var mTotal: Int?,
        @Json(name = "active") var mActive: Int?,
        @Json(name = "comment_list") var mComments: List<Post>?
)