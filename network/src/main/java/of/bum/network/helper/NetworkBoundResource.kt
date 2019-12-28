package of.bum.network.helper

import androidx.annotation.MainThread
import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import of.bum.network.AppExecutors
import timber.log.Timber

// ResultType: Type for the Resource data.
// RequestType: Type for the API response.
abstract class NetworkBoundResource<ResultType, RequestType>(
        private val appExecutors: AppExecutors
) {

    private val result = MediatorLiveData<Resource<ResultType>>()

    init
    {
        // update LiveData for loading status
        result.value = Resource.Loading(null)
        // loading db from local db
        @Suppress("LeakingThis")
        val dbSource = loadFromDb()
        // observe LiveData source from local db
        result.addSource(dbSource) { data ->
            result.removeSource(dbSource)
            if (shouldFetch(data)) {
                fetchFromNetwork(dbSource)
            } else {
                result.addSource(dbSource) { newData ->
                    setValue(Resource.Success(newData))
                }
            }
        }

    }

    @MainThread
    private fun setValue(newValue: Resource<ResultType>) {
        if (result.value != newValue) {
            result.value = newValue
        }
    }
    /**
     * 1) observe local db
     * 2) if <condition/> query the network
     * 3) stop observing the local db
     * 4) insert new data into local db
     * 5) begin observing local db again to see the refreshed data from network
     * @param dbSource
     */
    private fun fetchFromNetwork(dbSource: LiveData<ResultType>) {

        Timber.d("Fetch from network")
        // we re-attach dbSource as a new source, it will dispatch its latest value quickly
        // update LiveData for loading status
        result.addSource(dbSource) { newData ->
            setValue(Resource.Loading(newData))
        }

        val apiResponse = createCall()

        result.addSource(apiResponse) {response ->
            result.removeSource(apiResponse)
            result.removeSource(dbSource)

            /*
                    3 cases:
                       1) ApiSuccessResponse
                       2) ApiErrorResponse
                       3) ApiEmptyResponse
             */

            when(response) {
                is ApiSuccessResponse -> {
                    Timber.d("ApiSuccessResponse")
                    appExecutors.diskIO().execute {
                        saveCallResult(processResponse(response))
                        appExecutors.mainThread().execute{
                            result.addSource(loadFromDb()) {newData ->
                                setValue(Resource.Success(newData))
                            }
                        }
                    }
                }
                is ApiEmptyResponse -> {
                    Timber.d("ApiEmptyResponse")
                    appExecutors.mainThread().execute {
                        result.addSource(loadFromDb()) {newData ->
                            setValue(Resource.Success(newData))
                        }
                    }
                }
                is ApiErrorResponse -> {
                    Timber.d("ApiErrorResponse")
                    onFetchFailed()
                    result.addSource(dbSource) {newData ->
                        setValue(Resource.Error(response.errorMessage, newData))

                    }
                }
            }
        }
    }

    // Called with the data in the database to decide whether to fetch
    // potentially updated data from the network.
    @MainThread
    protected abstract fun shouldFetch(data: ResultType?): Boolean

    @WorkerThread
    protected open fun processResponse(response: ApiSuccessResponse<RequestType>) = response.body
    // Called to save the result of the API response into the database
    @WorkerThread
    protected abstract fun saveCallResult(item: RequestType)

    // Called to get the cached data from the database.
    @MainThread
    protected abstract fun loadFromDb(): LiveData<ResultType>

    // Called to create the API call.
    @MainThread
    protected abstract fun createCall(): LiveData<ApiResponse<RequestType>>

    // Called when the fetch fails. The child class may want to reset components
    // like rate limiter.
    protected open fun onFetchFailed() {}

    // Returns a LiveData object that represents the resource that's implemented
    // in the base class.
    fun asLiveData() = result as LiveData<Resource<ResultType>>
}
