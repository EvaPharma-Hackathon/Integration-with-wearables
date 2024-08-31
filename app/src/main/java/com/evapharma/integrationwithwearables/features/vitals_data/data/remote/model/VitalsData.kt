package com.evapharma.integrationwithwearables.features.vitals_data.data.remote.model

data class VitalsData(
    val steps: String? ,
    val calories: String? ,
    val sleep: String? ,
    val distance: String? ,
    val bloodSugar: String? ,
    val oxygenSaturation: String? ,
    val heartRate: String? ,
    val weight: String? ,
    val height: String? ,
    val temperature: String?,
    val bloodPressure: String?,
    val respiratoryRate: String?
)

