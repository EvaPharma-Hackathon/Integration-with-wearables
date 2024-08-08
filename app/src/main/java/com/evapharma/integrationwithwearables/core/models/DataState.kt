package com.evapharma.integrationwithwearables.core.models

import com.evapharma.integrationwithwearables.R
import com.evapharma.integrationwithwearables.core.utils.StringLocale
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import org.json.JSONException
import org.json.JSONObject
import retrofit2.HttpException
import retrofit2.Response
import java.net.UnknownHostException


sealed class DataState<out T>(
    val loading: Boolean,
    private val data: T? = null,
    val code: Int = 200,
    val reason: List<String>? = null, /* = java.util.List<kotlin.String> */
    val exception: Exceptions? = null
) {
    fun data(): T? = data

    object Idle : DataState<Nothing>(false)

    class Loading<T>(val cachedData: T? = null) : DataState<T>(
        loading = true,
        data = cachedData
    )

    class Error<T>(code: Int, reason: List<String>) : DataState<T>(
        loading = false,
        data = null,
        code = code,
//        reason = reason
    )

    data class ErrorV2<out T>(val e: Exceptions) : DataState<T>(loading = false, exception = e)

    data class Success<out T>(val data: T) : DataState<T>(
        loading = false,
        data = data
    )
}

fun <T> DataState<List<T>>.isEmptyList(): Boolean {
    return this is DataState.Success && this.data.isEmpty()
}

inline fun <reified T> Response<BaseResponse<T>>.handleResponse(): DataState<T> {

    return when (this.code()) {
        200 ->
            DataState.Success(responseConverter(this).data) as DataState<T>

        401 ->
            DataState.ErrorV2(Exceptions.Unauthorized)

        500 ->
            DataState.ErrorV2(Exceptions.InternalServerError)

        400 -> {
            val response = responseConverter(this)
            DataState.ErrorV2(
                Exceptions.ErrorResponse(
                    msg = response.message
                        ?: response.errorList.getOrNull(0)?.message
                        ?: StringLocale.instance.localize(R.string.something_went_wrong),
                    code = 400
                )
            )
        }

        else -> {
            DataState.ErrorV2(
                Exceptions.ErrorResponse(
                    msg = StringLocale.instance.localize(
                        R.string.something_went_wrong
                    )
                )
            )
        }
    }
}

inline fun <reified T> responseConverter(
    response: Response<BaseResponse<T>>
): BaseResponse<T> {
    return try {
        val gson = GsonBuilder().serializeNulls().create()

        if (!response.isSuccessful) {
            var errorResponse = ErrorResponse()
            if (response.errorBody() != null) {
                try {
                    val obj = JSONObject(response.errorBody()!!.string())
                    try {
                        val temp = Gson().fromJson(obj.toString(), ErrorResponse::class.java)
                        temp?.let { errorResponse = it }
                    } catch (e: IllegalStateException) {
                    }
                } catch (ex: JSONException) {
                }
            }
            errorResponse.code = response.code()
            errorResponse.name = response.message()
            throw errorResponse
        } else {

            BaseResponse<T>().apply {
                val son = Gson()
                val res = son.toJson(response.body()?.data)
                data = gson.fromJson(res, T::class.java)
            }
        }
    } catch (e: Exception) {
        throw e
    }
}

inline fun <reified T> Throwable.handleException(): DataState<T> {
    return when (this) {
        is HttpException ->
            DataState.ErrorV2<T>(
                Exceptions.ErrorResponse(
                    msg = StringLocale.instance.localize(
                        R.string.network_error
                    )
                )
            )

        is UnknownHostException ->
            DataState.ErrorV2(
                Exceptions.ErrorResponse(
                    msg = StringLocale.instance.localize(
                        R.string.no_internet
                    )
                )
            )

        else ->
            DataState.ErrorV2(
                Exceptions.ErrorResponse(
                    msg = this.message ?: StringLocale.instance.localize(
                        R.string.something_went_wrong
                    )
                )
            )
    }
}



