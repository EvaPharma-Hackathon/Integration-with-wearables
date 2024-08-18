package com.evapharma.integrationwithwearables.features.covid_cases.data.local.model

data class VitalsData (
    val metricValue: String,
    val dataType: DataType,
    val toDatetime: String,
    val fromDatetime: String
)

enum class DataType {
    STEPS, DISTANCE, SLEEP, CALORIES ,BLOOD_SUGAR , OXYGEN_SATURATION , HEART_RATE
}