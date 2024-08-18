package com.evapharma.integrationwithwearables.features.covid_cases.data.remote.model

data class VitalsData(
    val steps: String = "0",
    val calories: String = "0",
    val sleep: String = "0",
    val distance: String = "0",
    val bloodSugar: String = "0",
    val oxygenSaturation: String = "0",
    val heartRate: String = "0",
    val weight: String = "0",
    val height: String = "0",
    val temperature: String="0",
    val bloodPressure: String="0",
    val respiratoryRate: String="0"
)