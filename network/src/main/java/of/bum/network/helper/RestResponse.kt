package of.bum.network.helper

import com.squareup.moshi.Json
import com.anhtam.domain.Base

data class RestResponse<T>(
        @Json(name = "status") val status: String?,
        @Json(name = "data") val data: T?,
        @Json(name = "message") val message: String?,
        @Json(name = "meta") val mMeta: Map<String, Any>?
): Base()