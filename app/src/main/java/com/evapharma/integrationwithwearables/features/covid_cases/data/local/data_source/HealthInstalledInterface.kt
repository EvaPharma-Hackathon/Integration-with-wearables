package com.evapharma.integrationwithwearables.features.covid_cases.data.local.data_source

import android.content.Context

interface HealthInstalledInterface {
    fun checkForHealthConnectInstalled(context: Context): Int
    suspend fun checkPermissions(): Boolean
}