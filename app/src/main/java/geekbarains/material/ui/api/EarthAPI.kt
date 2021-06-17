package geekbarains.material.ui.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface EarthAPI {

    @GET("EPIC/api/natural/all")
    fun getEarth(@Query("api_key") apiKey: String): Call<EarthServerResponseData>
}