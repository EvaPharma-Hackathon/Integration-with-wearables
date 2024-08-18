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
        observeStepsData()
        observeCaloriesData()
        observeSleepData()
        observeDistanceData()
        observeBloodSugarData()
        observeOxygenData()
        observeHealthData()

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

    private fun observeStepsData() {
        lifecycleScope.launch {
            viewModel.steps.collect { steps ->
                binding.stepsInput.setText(steps)
            }
        }
    }

    private fun observeCaloriesData() {
        lifecycleScope.launch {
            viewModel.calories.collect { calories ->
                binding.caloriesInput.setText(calories)
            }
        }
    }

    private fun observeSleepData() {
        lifecycleScope.launch {
            viewModel.sleep.collect { sleep ->
                binding.sleepInput.setText(sleep)
            }
        }
    }

    private  fun observeDistanceData(){
        lifecycleScope.launch {
            viewModel.distance.collect{ distance->
                binding.distanceInput.setText(distance)
            }
        }
    }

    private fun observeBloodSugarData() {
        lifecycleScope.launch {
            viewModel.bloodSugar.collect { bloodSugar ->
                binding.bloodSugarInput.setText(bloodSugar)
            }
        }
    }

    private fun observeOxygenData() {
        lifecycleScope.launch {
           viewModel.oxygenSaturation.collect{ oxygenSaturation->
               binding.oxygenSaturationInput.setText(oxygenSaturation)
           }
        }
    }

   private fun observeHealthData() {
       lifecycleScope.launch {
           viewModel.heartRate.collect { healthData ->
               binding.heartRateInput.setText(healthData)
           }
       }
   }
}
