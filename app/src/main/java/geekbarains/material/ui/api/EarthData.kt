package geekbarains.material.ui.api

sealed class EarthData {
    data class Success(val serverResponseData: EarthServerResponseData) : EarthData()
    data class Error(val error: Throwable) : EarthData()
    data class Loading(val progress: Int?) : EarthData()
}