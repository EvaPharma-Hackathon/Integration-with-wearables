package com.evapharma.integrationwithwearables.core

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.evapharma.integrationwithwearables.R
import com.evapharma.integrationwithwearables.core.models.ErrorResponse
import com.evapharma.integrationwithwearables.core.models.Exceptions
import com.evapharma.integrationwithwearables.core.utils.StringLocale
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.net.UnknownHostException

open class BaseViewModel : ViewModel() {

    private var _errorStateFlow = MutableSharedFlow<Exceptions>()
    val errorStateFlow = _errorStateFlow

    suspend fun emitException(e: Exceptions) {
        _errorStateFlow.emit(e)
    }

    var cityName: String? = null


    inline fun CoroutineScope.launchCatching(
        noinline block: suspend CoroutineScope.() -> Unit,
        crossinline onError: (Throwable) -> Unit,

        ) {
        launch(
            CoroutineExceptionHandler { _, throwable -> onError(throwable) },
            block = block
        )
    }

    val userLoggedInLiveData: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }

    val errorLiveData: MutableLiveData<ErrorResponse> by lazy {
        MutableLiveData<ErrorResponse>()
    }

    fun handleError(t: Throwable) {
        val error: ErrorResponse = errorResponse(t)
        errorLiveData.postValue(error)
    }


    /**
     * this function is used to prevent duplicated code in
     * error handling.
     * for example, look at "handlePromoCodeError" function in
     * "BookingJourneyViewModel"
     * **/
    fun errorResponse(t: Throwable): ErrorResponse {

        return when (t) {
            is ErrorResponse -> {
                t
            }

            is HttpException -> {
                ErrorResponse(StringLocale.instance.localize(R.string.network_error))
            }

            is UnknownHostException -> {
                ErrorResponse(StringLocale.instance.localize(R.string.no_internet))
            }

            else -> {
                ErrorResponse(StringLocale.instance.localize(R.string.something_went_wrong))
            }
        }
    }
}
