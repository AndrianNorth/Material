package geekbarains.material.ui.api

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import geekbarains.material.BuildConfig
import geekbarains.material.ui.picture.PODRetrofitImpl
import geekbarains.material.ui.picture.PODServerResponseData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EarthViewModel(
    private val liveDataForViewToObserve: MutableLiveData<EarthData> = MutableLiveData(),
    private val retrofitImpl: EarthRetrofitImpl = EarthRetrofitImpl()
) :
    ViewModel() {

    fun getData(): LiveData<EarthData> {
        sendServerRequest()
        return liveDataForViewToObserve
    }

    private fun sendServerRequest() {
        liveDataForViewToObserve.value = EarthData.Loading(null)
        val apiKey: String = BuildConfig.NASA_API_KEY
        if (apiKey.isBlank()) {
            EarthData.Error(Throwable("You need API key"))
        } else {
            retrofitImpl.getRetrofitImpl().getEarth(apiKey).enqueue(object :
                Callback<EarthServerResponseData> {
                override fun onResponse(
                    call: Call<EarthServerResponseData>,
                    response: Response<EarthServerResponseData>
                ) {
                    if (response.isSuccessful && response.body() != null) {
                        liveDataForViewToObserve.value =
                            EarthData.Success(response.body()!!)
                    } else {
                        val message = response.message()
                        if (message.isNullOrEmpty()) {
                            liveDataForViewToObserve.value =
                                EarthData.Error(Throwable("Unidentified error"))
                        } else {
                            liveDataForViewToObserve.value =
                                EarthData.Error(Throwable(message))
                        }
                    }
                }

                override fun onFailure(call: Call<EarthServerResponseData>, t: Throwable) {
                    liveDataForViewToObserve.value = EarthData.Error(t)
                }
            })
        }
    }
}
