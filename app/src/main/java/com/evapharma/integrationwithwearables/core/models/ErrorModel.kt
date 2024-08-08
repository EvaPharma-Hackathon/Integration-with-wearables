package com.evapharma.integrationwithwearables.core.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
class ErrorModel(
    @SerializedName("id") val id: Int?,
    @SerializedName("message") val message: String?,
) : Parcelable