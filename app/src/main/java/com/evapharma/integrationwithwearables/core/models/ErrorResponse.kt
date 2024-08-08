package com.evapharma.integrationwithwearables.core.models

import android.os.Parcelable
import com.evapharma.integrationwithwearables.core.utils.Constants
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
class ErrorResponse(
    @SerializedName("message") override val message: String? = null,
    @SerializedName("errorList") val errorList: ArrayList<ErrorModel> = ArrayList(),
    var name: String? = null,
    var code: Int = 0,
) : Exception(), Parcelable {

    override fun toString(): String {
        var error = Constants.EMPTY_STRING
        if (message != null && message.isNotEmpty())
            error += message
        else if (name != null)
            error += name
        return error.trim()
    }
}