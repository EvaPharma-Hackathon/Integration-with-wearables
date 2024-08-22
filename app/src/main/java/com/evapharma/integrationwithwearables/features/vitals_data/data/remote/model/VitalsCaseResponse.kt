package com.evapharma.integrationwithwearables.features.vitals_data.data.remote.model

data class VitalsCaseResponse(
    val data: Data?,
    val message: String?,
    val success: Boolean?
)