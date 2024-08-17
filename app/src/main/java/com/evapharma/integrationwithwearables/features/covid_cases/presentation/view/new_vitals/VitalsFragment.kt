package com.evapharma.integrationwithwearables.features.covid_cases.presentation.view.new_vitals

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.health.connect.client.HealthConnectClient
import androidx.health.connect.client.PermissionController
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.evapharma.integrationwithwearables.core.BaseFragment
import com.evapharma.integrationwithwearables.core.dialogs.PermissionRationaleDialog
import com.evapharma.integrationwithwearables.core.utils.requiredHealthPermission
import com.evapharma.integrationwithwearables.databinding.FragmentVitalsBinding
import com.evapharma.integrationwithwearables.features.covid_cases.presentation.viewmodels.VitalsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class VitalsFragment : BaseFragment<FragmentVitalsBinding, VitalsViewModel>() {

    private val requiredPermissions = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        listOf(
            Manifest.permission.BLUETOOTH_SCAN,
            Manifest.permission.BLUETOOTH_CONNECT,
            Manifest.permission.BLUETOOTH_ADVERTISE,
            Manifest.permission.ACCESS_FINE_LOCATION
        ).toTypedArray()
    } else {
        listOf(
            Manifest.permission.BLUETOOTH,
            Manifest.permission.BLUETOOTH_ADMIN,
            Manifest.permission.ACCESS_FINE_LOCATION
        ).toTypedArray()
    }

    private lateinit var requestPermissionLauncher: ActivityResultLauncher<Array<String>>
    private lateinit var healthPermissionLauncher: ActivityResultLauncher<Set<String>>

    override fun initBinding(): FragmentVitalsBinding {
        return FragmentVitalsBinding.inflate(layoutInflater)
    }

    override fun initViewModel() {
        viewModel = ViewModelProvider(requireActivity())[VitalsViewModel::class.java]
    }

    override fun onFragmentCreated() {
        setupPermissionLauncher()
        setupHealthPermissionLauncher()

        if (!arePermissionsGranted(requireContext(), requiredPermissions.toList())) {
            requestPermissions(requiredPermissions)
        } else {
            checkHealthConnectStatus()
        }

        requestHealthConnectPermissions()
    }

    private fun setupPermissionLauncher() {
        requestPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            val allPermissionsGranted = permissions.values.all { it }
            if (allPermissionsGranted) {
                Log.i("TAG", "setupPermissionLauncher: Done")
                checkHealthConnectStatus()
            } else {
                showPermissionRationale()
            }
        }
    }

    private fun setupHealthPermissionLauncher() {
        healthPermissionLauncher = registerForActivityResult(
            PermissionController.createRequestPermissionResultContract()
        ) { granted ->
            if (granted.containsAll(requiredHealthPermission)) {
                fetchHealthData()
            } else {
                showPermissionRationale()
            }
        }
    }

    private fun requestPermissions(permissions: Array<String>) {
        requestPermissionLauncher.launch(permissions)
    }

    private fun showPermissionRationale() {
        val dialog = PermissionRationaleDialog(requireContext())
        dialog.show()
    }

    private fun arePermissionsGranted(context: Context, permissions: List<String>): Boolean {
        return permissions.all { permission ->
            ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED
        }
    }

    private fun checkHealthConnectStatus() {
        viewModel.checkHealthConnectInstituted(requireContext())
        viewModel.checkPermissions()
    }

    private fun requestHealthConnectPermissions() {
        val healthConnectClient = HealthConnectClient.getOrCreate(requireContext())

        lifecycleScope.launch {
            val grantedPermissions = healthConnectClient.permissionController.getGrantedPermissions()

            if (!grantedPermissions.containsAll(requiredHealthPermission)) {
                healthPermissionLauncher.launch(requiredHealthPermission)
            } else {
                fetchHealthData()
            }
        }
    }

    private fun fetchHealthData() {

    }
}
