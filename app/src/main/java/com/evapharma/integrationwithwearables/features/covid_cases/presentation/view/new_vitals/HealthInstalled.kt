package com.evapharma.integrationwithwearables.features.covid_cases.presentation.view.new_vitals

import android.content.Context
import android.widget.Toast
import androidx.health.connect.client.HealthConnectClient
import com.evapharma.integrationwithwearables.core.utils.requiredHealthPermission


class HealthInstalled {

    private var healthConnectClient: HealthConnectClient? = null

    fun checkForHealthConnectInstalled(context: Context): Int {
        val availabilityStatus = HealthConnectClient.getSdkStatus(context, "com.google.android.apps.healthdata")
        if (availabilityStatus == HealthConnectClient.SDK_AVAILABLE) {
            healthConnectClient = HealthConnectClient.getOrCreate(context)
        }
        return availabilityStatus
    }

    suspend fun checkPermissions(): Boolean {
        val granted = healthConnectClient?.permissionController?.getGrantedPermissions()
        return granted?.containsAll(requiredHealthPermission) ?: false
    }
}
