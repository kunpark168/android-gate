package com.anhtam.domain.v2

import com.anhtam.domain.v2.protocol.User
import com.squareup.moshi.Json

data class Userv2(
        @Json(name = "userId")
        override var mId: Int? = null,
        @Json(name = "email")
        override var mEmail: String? = null,
        @Json(name = "avatar")
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
        override var mPoint: Int? = null,
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
        override var mFlower: Long? = null,
        @Json(name = "flowing")
        override var mFlowing: Long? = null,
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
        override var mIsFollowing: Boolean? = null,
        @Json(name = "appellation")
        override var mRanking: String? = null,
        @Json(name = "first_name")
        override var mFirstName: String? = null,
        @Json(name = "last_name")
        override var mLastName: String? = null,
        @Json(name = "location")
        override var mLocation: String? = null,
        @Json(name = "user_name")
        override var mUserName: String? = null,
        override var mAppellation: String? = null,
        @Json(name = "theme")
        override var mTheme: String? = null,
        @Json(name = "web")
        override var mWeb: String? = null,
        @Json(name = "point_use")
        override var mPointUse: Int? = null
): User