package com.evapharma.integrationwithwearables.features.covid_cases.data.local.data_source

import android.content.Context
import android.widget.Toast
import androidx.health.connect.client.HealthConnectClient
import com.evapharma.integrationwithwearables.core.utils.requiredHealthPermission
import javax.inject.Inject


class HealthInstalled @Inject constructor():HealthInstalledInterface {
    private var healthConnectClient: HealthConnectClient? = null
    override fun checkForHealthConnectInstalled(context: Context): Int {
        val availabilityStatus =
            HealthConnectClient.getSdkStatus(context, "com.google.android.apps.healthdata")
        when (availabilityStatus) {
            HealthConnectClient.SDK_UNAVAILABLE -> {
                Toast.makeText(context, "Health Connect is not available on this device.", Toast.LENGTH_LONG).show()
            }
            HealthConnectClient.SDK_UNAVAILABLE_PROVIDER_UPDATE_REQUIRED -> {
                Toast.makeText(context, "Please update your Health Connect provider.", Toast.LENGTH_LONG).show()
            }
            HealthConnectClient.SDK_AVAILABLE -> {
                healthConnectClient = HealthConnectClient.getOrCreate(context)
            }
        }
        return availabilityStatus
    }

   override  suspend fun checkPermissions(): Boolean {
        val granted = healthConnectClient?.permissionController?.getGrantedPermissions()
        return granted?.containsAll(requiredHealthPermission) ?: false
    }
}