package of.bum.network.helper

import com.squareup.moshi.Json
import com.anhtam.domain.Base

data class RestResponse<T>(
        @Json(name = "status") val status: String?,
        @Json(name = "data") val data: T?,
        @Json(name = "banner") val banner: T?,
        @Json(name = "games") val game: T?,
        @Json(name = "favorite") val favorite: T?,
        @Json(name = "download") val download: T?,
        @Json(name = "message") val message: String?,
        @Json(name = "total_comment") val totalComment: Long?
): Base()