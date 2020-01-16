package com.anhtam.domain.v2

import com.anhtam.domain.v2.protocol.User
import com.squareup.moshi.Json

data class Userv1(
        @Json(name = "user_id")
        override var mId: Int? = null,
        @Json(name = "email")
        override var mEmail: String? = null,
        @Json(name = "avatar_path")
        override var mAvatar: String? = null,
        @Json(name = "status")
        override var mStatus: String? = null,
        @Json(name = "role_id")
        override var mRoleId: String? = null,
        @Json(name = "created_date")
        override var mCreatedDate: String? = null,
        @Json(name = "name")
        override var mName: String? = null,
        @Json(name = "gender")
        override var mGender: String? = null,
        @Json(name = "phone")
        override var mPhone: String? = null,
        @Json(name = "lang_id")
        override var mLangId: String? = null,
        @Json(name = "point")
        override var mPoint: String? = null,
        @Json(name = "birth")
        override var mBirth: String? = null,
        @Json(name = "num_like")
        override var mNumLike: String? = null,
        @Json(name = "game_types")
        override var mGameTypes: String? = null,
        @Json(name = "access_token")
        override var mAccessToken: String? = null,
        @Json(name = "note")
        override var mNote: String? = null,
        @Json(name = "facebook")
        override var mFacebook: String? =  null,
        @Json(name = "flower")
        override var mFlower: String? = null,
        @Json(name = "flowing")
        override var mFlowing: String? = null,
        @Json(name = "last_login")
        override var mLastLogin: String? = null,
        @Json(name = "address")
        override var mAddress: String? = null,
        @Json(name = "qq")
        override var mQQ: String? = null,
        @Json(name = "skype")
        override var mSkype: String? = null,
        @Json(name = "total_post")
        override var mTotalPost: String? = null,
        @Json(name = "following")
        override var mIsFollowing: String? = null

): User