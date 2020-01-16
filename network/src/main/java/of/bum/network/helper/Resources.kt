package of.bum.network.helper

import retrofit2.Response

// A generic class that contains data and status about loading this data.
sealed class Resource<T>(
        val data: T? = null,
        val message: String? = null,
        val mResponse: ApiSuccessResponse<*>? = null
) {
    class Success<T>(data: T?, response: ApiSuccessResponse<*>? = null) : Resource<T>(data, null, response)
    class Loading<T>(data: T? = null) : Resource<T>(data)
    class Error<T>(message: String, data: T? = null) : Resource<T>(data, message)
}
