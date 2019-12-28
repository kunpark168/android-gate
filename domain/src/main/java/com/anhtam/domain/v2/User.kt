package com.anhtam.domain.v2
import com.squareup.moshi.Json
import java.util.*


data class User(
    @Json(name = "user_id")
    var mUserId: Int? = null,
    @Json(name = "userId")
    var mId: Int? = null,
    @Json(name = "email")
    var mEmail: String? = null,
    @Json(name = "password")
    var mPassword: String? = null,
    @Json(name = "avatar_path")
    var mAvatarPath: String? = null,
    @Json(name = "status")
    var mStatus: Int? = null,
    @Json(name = "role_id")
    var mRoleId: Int? = null,
    @Json(name = "note")
    var mNote: String? = null,
    @Json(name = "created_date")
    var mCreatedDate: String? = null,
    @Json(name = "facebook")
    var mFacebook: String? =  null,
    @Json(name = "name")
    var mName: String? = null,
    @Json(name = "flower")
    var mFlower: String? = null,
    @Json(name = "flowing")
    var mFlowing: String? = null,
    @Json(name = "avatar")
    var mAvatar: String? = null,
    @Json(name = "last_login")
    var mLastLogin: String? = null,
    @Json(name = "gender")
    var mGender: Int? = null,
    @Json(name = "address")
    var mAddress: String? = null,
    @Json(name = "phone")
    var mPhone: String? = null,
    @Json(name = "qq")
    var mQQ: String? = null,
    @Json(name = "skype")
    var mSkype: String? = null,
    @Json(name = "lang_id")
    var mLangId: Int? = null,
    @Json(name = "birth")
    var mBirth: String? = null,
    @Json(name = "point")
    var mPoint: String? = null,
    @Json(name = "game_types")
    var mGameTypes: String? = null,
    @Json(name = "access_token")
    var mAccessToken: String? = null
)