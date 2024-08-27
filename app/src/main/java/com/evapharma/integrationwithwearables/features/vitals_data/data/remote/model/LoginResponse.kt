package com.evapharma.integrationwithwearables.features.vitals_data.data.remote.model

import com.google.gson.annotations.SerializedName

data class LoginRequest(
    @SerializedName("phoneNumber")
    val phoneNumber:String
)

/*
data class LoginResponse(
    @SerializedName("data")
    val data:String

)*/
