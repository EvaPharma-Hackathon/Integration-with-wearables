package com.evapharma.integrationwithwearables.features.vitals_data.data.remote.model

data class NewVitalsRequest(
    val time: String,
    val sleep: Int,
    val steps: Int,
    val distance: Int,
    val bloodPressure: String,
    val bloodSugar: Int,
    val oxygenSaturation: Int,
    val heartRate: Int,
    val respiratoryRate: Int,
    val temperature: Int,
    val weight: Int,
    val height: Int,
    val smoker: String,
    val drinkAlcohol: String
)
