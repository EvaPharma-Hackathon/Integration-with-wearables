package com.evapharma.integrationwithwearables.features.vitals_data.presentation.view.new_vitals

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.core.content.ContextCompat
import androidx.health.connect.client.HealthConnectClient
import androidx.health.connect.client.PermissionController
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.evapharma.integrationwithwearables.R
import com.evapharma.integrationwithwearables.core.BaseFragment
import com.evapharma.integrationwithwearables.core.dialogs.ErrorDialog
import com.evapharma.integrationwithwearables.core.dialogs.PermissionDialog
import com.evapharma.integrationwithwearables.core.utils.requiredHealthPermission
import com.evapharma.integrationwithwearables.databinding.FragmentVitalsBinding
import com.evapharma.integrationwithwearables.features.vitals_data.data.remote.model.NewVitalsRequest
import com.evapharma.integrationwithwearables.features.vitals_data.presentation.viewmodels.VitalsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.TimeZone


@AndroidEntryPoint
class VitalsFragment : BaseFragment<FragmentVitalsBinding, VitalsViewModel>() {

    private lateinit var healthPermissionLauncher: ActivityResultLauncher<Set<String>>
    private val healthInstalled = HealthInstalled()

    private var selectedSmokerTextView: TextView? = null
    private var selectedAlcoholTextView: TextView? = null

    override fun initBinding(): FragmentVitalsBinding {
        return FragmentVitalsBinding.inflate(layoutInflater)
    }

    override fun initViewModel() {
        viewModel = ViewModelProvider(requireActivity())[VitalsViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setupHealthPermissionLauncher()

        return super.onCreateView(inflater, container, savedInstanceState)
    }
    override fun onFragmentCreated() {
        checkHealthConnectStatus()
        observeVitalsData()
        setupRadioGroupListeners()
        addVitals()
        observeAddVitalsState()
    }

    private fun setupHealthPermissionLauncher() {
        healthPermissionLauncher = registerForActivityResult(
            PermissionController.createRequestPermissionResultContract()
        ) { granted ->
            if (granted.containsAll(requiredHealthPermission)) {
                viewModel.fetchHealthData()
            }
        }
    }

    private fun checkHealthConnectStatus() {
        lifecycleScope.launch {
            when ( healthInstalled.checkForHealthConnectInstalled(requireContext())) {
                HealthConnectClient.SDK_UNAVAILABLE -> {
                    showErrorDialog((R.string.sdk_unavailable_message).toString())
                }
                HealthConnectClient.SDK_UNAVAILABLE_PROVIDER_UPDATE_REQUIRED -> {
                    showHealthConnectDownloadDialog()
                }
                HealthConnectClient.SDK_AVAILABLE -> {
                    if (!healthInstalled.checkPermissions()) {
                        healthPermissionLauncher.launch(requiredHealthPermission)
                    } else {
                        viewModel.fetchHealthData()
                    }
                }
                else -> {
                    showErrorDialog(getString(R.string.unexpected_error_message))
                }
            }
        }
    }


    private fun showErrorDialog(message: String) {
        val errorDialog = ErrorDialog(requireContext())
        errorDialog.message = message
        errorDialog.show()
    }

    private fun showHealthConnectDownloadDialog() {
        val permissionDialog = PermissionDialog(requireContext())
        permissionDialog.show()
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
                binding.bodyTemperatureInput.setText(data.temperature)
                binding.bloodPressureInput.setText(data.bloodPressure)
                binding.respiratoryRateInput.setText(data.respiratoryRate)

            }
        }
    }

    private fun setupRadioGroupListeners() {
        setupTextViewSelection(binding.yseSmokerText, binding.noSmokerText) { selectedTextView ->
            selectedSmokerTextView?.apply {
                setBackgroundColor(ContextCompat.getColor(requireContext(), android.R.color.transparent))
                setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
            }

            selectedTextView.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.request_fg))
            selectedTextView.setTextColor(ContextCompat.getColor(requireContext(), R.color.primary_shade_400))
            selectedSmokerTextView = selectedTextView
            Log.i("Smoker Selection", selectedTextView.text.toString())
        }
        binding.noSmokerText.performClick()
        setupTextViewSelection(binding.yesDrinkAlcoholText, binding.noDrinkAlcoholText) { selectedTextView ->
            selectedAlcoholTextView?.apply {
                setBackgroundColor(ContextCompat.getColor(requireContext(), android.R.color.transparent))
                setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
            }

            selectedTextView.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.request_fg))
            selectedTextView.setTextColor(ContextCompat.getColor(requireContext(), R.color.primary_shade_400))
            selectedAlcoholTextView = selectedTextView
            Log.i("Alcohol Selection", selectedTextView.text.toString())
        }
        binding.noDrinkAlcoholText.performClick()
    }

    private fun setupTextViewSelection(vararg textViews: TextView, onSelection: (TextView) -> Unit) {
        for (textView in textViews) {
            textView.setOnClickListener {
                onSelection(textView)
            }
        }
    }

    private fun addVitals(){
        binding.addNowButton.setOnClickListener {
            addNewVitals()
        }
    }

    private fun addNewVitals(){
        val formattedTime = LocalDateTime.now()
            .atZone(TimeZone.getDefault().toZoneId())
            .minusMinutes(1)
            .plusSeconds(59)
            .format(DateTimeFormatter.ISO_OFFSET_DATE_TIME)
        val newVitals = NewVitalsRequest(
            time = formattedTime,
            sleep = binding.sleepInput.text.toString().toInt(),
            steps = binding.stepsInput.text.toString().toInt(),
            distance = binding.distanceInput.text.toString().toFloat().toInt(),
            bloodPressure = binding.bloodPressureInput.text.toString(),
            bloodSugar = binding.bloodSugarInput.text.toString().toFloat().toInt(),
            oxygenSaturation = binding.oxygenSaturationInput.text.toString().toFloat().toInt(),
            heartRate = binding.heartRateInput.text.toString().toFloat().toInt(),
            respiratoryRate = binding.respiratoryRateInput.text.toString().toFloat().toInt(),
            temperature = binding.bodyTemperatureInput.text.toString().toFloat().toInt(),
            weight = binding.weightInput.text.toString().toFloat().toInt(),
            height = binding.heightInput.text.toString().toFloat().toInt(),
            smoker = selectedSmokerTextView?.text.toString(),
            drinkAlcohol = selectedAlcoholTextView?.text.toString(),

        )
              viewModel.addNewVitals(newVitals)

    }

    private fun observeAddVitalsState() {
        lifecycleScope.launch {
            viewModel.addNewVitalsState.collect { viewState ->
                if (viewState.isLoading) {
                    binding.progressBar.visibility = View.VISIBLE
                } else {
                    binding.progressBar.visibility = View.GONE
                    if (viewState.isIdle) {
                        Toast.makeText(requireContext(), "Vitals added successfully", Toast.LENGTH_SHORT).show()
                    } else if (viewState.error != null) {
                        Toast.makeText(requireContext(), "Failed to add vitals", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}