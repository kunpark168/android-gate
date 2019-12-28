package of.bum.network

import androidx.annotation.MainThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import of.bum.network.helper.*
import timber.log.Timber

// ResultType: Type for the Resource data.  -- Resource<D>
// RequestType: Type for the API response.  -- RestResponse<D>
abstract class FetchBoundResource<RequestType> {

    private val result = MediatorLiveData<Resource<RequestType>>()

    init
    {
        // update LiveData for loading status
        result.value = Resource.Loading(null)
        // loading db from local db
        @Suppress("LeakingThis")
        if(shouldCheckAuth()) {
            checkAuth() // Success ->
            // fail -> continue
        }
        fetchFromNetwork()
    }

    private fun fetchFromNetwork() {
        Timber.d("Fetch from network")
        val apiResponse = createCall()

        result.addSource(apiResponse) {response ->
            /*
                    3 cases:
                       1) ApiSuccessResponse
                       2) ApiErrorResponse
                       3) ApiEmptyResponse
             */

            when(response) {
                is ApiSuccessResponse -> {
                    Timber.d("ApiSuccessResponse")
                    val unwrapped: RequestType? = response.body.run {
                        data?.let { return@run data}
                        banner?.let { return@run banner }
                        game?.let { return@run game }
                        favorite?.let { return@run favorite}
                        download?.let { return@run download }
                    }
                    result.value = Resource.Success(unwrapped)
                }
                is ApiEmptyResponse -> {
                    Timber.d("ApiEmptyResponse")
                    result.value = Resource.Error("Empty")
                }
                is ApiErrorResponse -> {
                    Timber.d("ApiErrorResponse")
                    onFetchFailed()
                    result.value = Resource.Error("Error")
                }
            }
        }
    }

    protected fun shouldCheckAuth() = true

    private fun checkAuth() {

    }

    @MainThread
    protected abstract fun  createCall(): LiveData<ApiResponse<RestResponse<RequestType>>>
    protected open fun onFetchFailed() {}
    fun asLiveData() = result as LiveData<Resource<RequestType>>
}
