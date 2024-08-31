package com.evapharma.integrationwithwearables.features.vitals_data.presentation.view.new_vitals

import android.content.Context
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
        return healthConnectClient?.let {
            val granted = it.permissionController.getGrantedPermissions()
            granted.containsAll(requiredHealthPermission)
        } ?: false
    }
}

