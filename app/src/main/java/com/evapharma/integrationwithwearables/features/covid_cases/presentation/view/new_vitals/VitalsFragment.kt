package com.evapharma.integrationwithwearables.features.covid_cases.presentation.view.new_vitals

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.health.connect.client.HealthConnectClient
import androidx.health.connect.client.PermissionController
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.evapharma.integrationwithwearables.core.BaseFragment
import com.evapharma.integrationwithwearables.core.utils.requiredHealthPermission
import com.evapharma.integrationwithwearables.databinding.FragmentVitalsBinding
import com.evapharma.integrationwithwearables.features.covid_cases.presentation.viewmodels.VitalsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class VitalsFragment : BaseFragment<FragmentVitalsBinding, VitalsViewModel>() {

    private lateinit var healthPermissionLauncher: ActivityResultLauncher<Set<String>>
    private val healthInstalled = HealthInstalled()

    override fun initBinding(): FragmentVitalsBinding {
        return FragmentVitalsBinding.inflate(layoutInflater)
    }

    override fun initViewModel() {
        viewModel = ViewModelProvider(requireActivity())[VitalsViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupHealthPermissionLauncher()
    }

    override fun onFragmentCreated() {
        checkHealthConnectStatus()
        observeVitalsData()
    }

    private fun setupHealthPermissionLauncher() {
        healthPermissionLauncher = registerForActivityResult(
            PermissionController.createRequestPermissionResultContract()
        ) { granted ->
            if (granted.containsAll(requiredHealthPermission)) {
                viewModel.fetchHealthData()
                Log.i("VitalsFragment", "Permissions granted")
            } else {
                Log.i("VitalsFragment", "Permissions denied")
            }
        }
    }

    private fun checkHealthConnectStatus() {
        lifecycleScope.launch {
            when (healthInstalled.checkForHealthConnectInstalled(requireContext())) {
                HealthConnectClient.SDK_UNAVAILABLE -> {
                    Toast.makeText(context, "Health Connect is not available on this device.", Toast.LENGTH_SHORT).show()
                }
                HealthConnectClient.SDK_UNAVAILABLE_PROVIDER_UPDATE_REQUIRED -> {
                    showHealthConnectUpdateDialog()
                }
                HealthConnectClient.SDK_AVAILABLE -> {
                    if (!healthInstalled.checkPermissions()) {
                        healthPermissionLauncher.launch(requiredHealthPermission)
                    } else {
                        viewModel.fetchHealthData()
                    }
                }
            }
        }
    }

    private fun showHealthConnectUpdateDialog() {
        Log.i("TAG", "showHealthConnectUpdateDialog: Please update your Health Connect provider to continue")
    }
    private fun observeVitalsData() {
        lifecycleScope.launch {
            viewModel.vitalsData.collect { data ->
                binding.stepsInput.setText(data.steps)
                binding.caloriesInput.setText(data.calories)
                binding.sleepInput.setText(data.sleep)
                binding.distanceInput.setText(data.distance)
                binding.bloodSugarInput.setText(data.bloodSugar)
                binding.oxygenSaturationInput.setText(data.oxygenSaturation)
                binding.heartRateInput.setText(data.heartRate)
                binding.weightInput.setText(data.weight)
                binding.heightInput.setText(data.height)
             //   binding.bodyTemperatureInput.setText(data.temperature)
            }
        }
    }
}
