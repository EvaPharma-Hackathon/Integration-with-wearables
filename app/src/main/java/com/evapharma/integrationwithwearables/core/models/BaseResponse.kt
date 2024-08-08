package com.evapharma.integrationwithwearables.core.models

import androidx.annotation.Keep
import com.evapharma.integrationwithwearables.core.utils.Constants
import com.google.gson.annotations.SerializedName
import retrofit2.HttpException
import retrofit2.Response

@Keep
data class BaseResponse<T>(
    @SerializedName("message")
    var message: String? = null,

    @SerializedName("errorList")
    val errorList: ArrayList<ErrorModel> = ArrayList(),

    @SerializedName("data")
    var data: T? = null
)

fun <T> Response<BaseResponse<T>>.getDataState(): DataState<T> {
    val responseBody = body()
    return if (isSuccessful && (responseBody != null) && (responseBody.data != null)) {
        DataState.Success(data = responseBody.data) as DataState<T>
    } else {
        DataState.Error(
            code = Constants.LOCAL_ERROR_CODE,
            reason = listOf(HttpException(this).toString())
        )
    }
}

fun <T> Response<T>.getDataStateV2(): DataState<T> {
    val responseBody = body()
    return if (isSuccessful && (responseBody != null)) {
        DataState.Success(data = responseBody)
    } else {
        DataState.Error(
            code = Constants.LOCAL_ERROR_CODE,
            reason = listOf(HttpException(this).toString())
        )
    }
}