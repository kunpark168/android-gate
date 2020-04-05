package com.anhtam.gate9.vo

import com.anhtam.domain.v2.Userv1
import com.squareup.moshi.Json

data class Letter(
    @Json(name = "message_id") var mId: Long?,
    @Json(name = "title") var mTitle: String?,
    @Json(name = "content") var mContent: String?,
    @Json(name = "create_date") var mCreatedDate: String?,
    @Json(name = "sender") var mSender: Userv1?,
    @Json(name = "receiver") var mReceiver: Userv1?,
    @Json(name = "inbox") var mInbox: Int?,
    @Json(name = "outbox") var mOutbox: Int?,
    @Json(name = "none_read") var mNoneRead: Int?,
    @Json(name = "read") var mRead: Boolean?
)