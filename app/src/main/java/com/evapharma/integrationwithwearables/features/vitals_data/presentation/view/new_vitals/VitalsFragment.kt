package com.evapharma.integrationwithwearables.features.vitals_data.presentation.view.new_vitals

import android.content.Context
import android.content.Intent
import android.provider.Settings
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.activity.result.ActivityResultLauncher
import androidx.core.content.ContextCompat
import androidx.health.connect.client.HealthConnectClient
import androidx.health.connect.client.PermissionController
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.evapharma.integrationwithwearables.R
import com.evapharma.integrationwithwearables.core.BaseFragment
import com.evapharma.integrationwithwearables.core.dialogs.ConfirmationDialog
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
        val permissionDialog = PermissionDialog(requireContext()) {
            redirectToHealthConnectSettings()
        }
        permissionDialog.show()
    }

    private fun redirectToHealthConnectSettings() {
        val playStoreIntent = Intent(Intent.ACTION_VIEW).apply {
            data = Uri.parse("https://play.google.com/store/apps/details?id=com.google.android.apps.healthdata")
            setPackage("com.android.vending")
        }
        startActivity(playStoreIntent)
    }

    private fun showPermissionDialog() {
        val permissionDialog = PermissionDialog(requireContext()) {
            healthPermissionLauncher.launch(requiredHealthPermission)

        }
        permissionDialog.show()
    }

    private fun observeVitalsData() {
        lifecycleScope.launch {
            viewModel.vitalsData.collect { data ->
                Log.i("TAG", "observeVitalsData: $data")
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

    private fun addNewVitals() {
        if (!validateInputData()) return


        val formattedTime = LocalDateTime.now()
            .atZone(TimeZone.getDefault().toZoneId())
            .minusMinutes(1)
            .plusSeconds(59)
            .format(DateTimeFormatter.ISO_OFFSET_DATE_TIME)
        val newVitals = NewVitalsRequest(
            time = formattedTime,
            sleep = binding.sleepInput.text?.toString()?.toIntOrNull() ?: 0,
            steps = binding.stepsInput.text?.toString()?.toIntOrNull() ?: 0,
            distance = binding.distanceInput.text?.toString()?.toFloatOrNull()?.toInt() ?: 0,
            bloodPressure = binding.bloodPressureInput.text?.toString().takeIf { !it.isNullOrEmpty() } ?: "0",
            bloodSugar = binding.bloodSugarInput.text?.toString()?.toFloatOrNull()?.toInt() ?: 0,
            oxygenSaturation = binding.oxygenSaturationInput.text?.toString()?.toFloatOrNull()?.toInt() ?: 0,
            heartRate = binding.heartRateInput.text?.toString()?.toFloatOrNull()?.toInt() ?: 0,
            respiratoryRate = binding.respiratoryRateInput.text?.toString()?.toFloatOrNull()?.toInt() ?: 0,
            temperature = binding.bodyTemperatureInput.text?.toString()?.toFloatOrNull()?.toInt() ?: 0,
            weight = binding.weightInput.text?.toString()?.toFloatOrNull()?.toInt() ?: 0,
            height = binding.heightInput.text?.toString()?.toFloatOrNull()?.toInt() ?: 0,
            smoker = selectedSmokerTextView?.text?.toString() ?: "no",
            drinkAlcohol = selectedAlcoholTextView?.text?.toString() ?: "no"
        )
        viewModel.addNewVitals(newVitals)
        observeAddVitalsState()

    }
    private fun observeAddVitalsState() {
        lifecycleScope.launch {
            viewModel.addNewVitalsState.collect { viewState ->
                if (viewState.isLoading) {
                    binding.progressBar.visibility = View.VISIBLE
                } else {
                    binding.progressBar.visibility = View.GONE
                    if (viewState.isIdle) {
                        showConfirmationDialog()
                    } else if (viewState.error != null) {
                        showErrorDialog("Failed to add vitals")
                    }
                }
            }
        }
    }

    private fun validateInputData(): Boolean {
        var isValid = true
        val allFieldsEmpty = binding.stepsInput.text.isNullOrEmpty() &&
                binding.caloriesInput.text.isNullOrEmpty() &&
                binding.sleepInput.text.isNullOrEmpty() &&
                binding.distanceInput.text.isNullOrEmpty() &&
                binding.bloodSugarInput.text.isNullOrEmpty() &&
                binding.oxygenSaturationInput.text.isNullOrEmpty() &&
                binding.heartRateInput.text.isNullOrEmpty() &&
                binding.weightInput.text.isNullOrEmpty() &&
                binding.heightInput.text.isNullOrEmpty() &&
                binding.bodyTemperatureInput.text.isNullOrEmpty() &&
                binding.bloodPressureInput.text.isNullOrEmpty() &&
                binding.respiratoryRateInput.text.isNullOrEmpty()
        if (allFieldsEmpty) {
            showErrorDialog("please enter your vitals data.")
            return false
        }
        if (!validateBloodPressure(binding.bloodPressureInput.text.toString())) {
            binding.bloodPressureInput.error = "not valid"
            isValid = false
        }

        if (!validateTemperature(binding.bodyTemperatureInput.text.toString())) {
            binding.bodyTemperatureInput.error = "not valid"
            isValid = false
        }

        if (!validateHeartRate(binding.heartRateInput.text.toString())) {
            binding.heartRateInput.error = "not valid"
            isValid = false
        }

        if (!validateSteps(binding.stepsInput.text.toString())) {
            binding.stepsInput.error = "not valid"
            isValid = false
        }

        if (!validateDistance(binding.distanceInput.text.toString())) {
            binding.distanceInput.error = "not valid"
            isValid = false
        }

        if (!validateOxygenSaturation(binding.oxygenSaturationInput.text.toString())) {
            binding.oxygenSaturationInput.error = "not valid"
            isValid = false
        }

        if (!validateRespiratoryRate(binding.respiratoryRateInput.text.toString())) {
            binding.respiratoryRateInput.error = "not valid"
            isValid = false
        }

        if (!validateWeight(binding.weightInput.text.toString())) {
            binding.weightInput.error = "not valid"
            isValid = false
        }

        if (!validateHeight(binding.heightInput.text.toString())) {
            binding.heightInput.error = "not valid"
            isValid = false
        }

        if (!validateCalories(binding.caloriesInput.text.toString())) {
            binding.caloriesInput.error = "not valid"
            isValid = false
        }

        if (!validateSleep(binding.sleepInput.text.toString())) {
            binding.sleepInput.error = "not valid"
            isValid = false
        }

        if (!validateBloodSugar(binding.bloodSugarInput.text.toString())) {
            binding.bloodSugarInput.error = "not valid"
            isValid = false
        }

        return isValid
    }

    private fun validateBloodPressure(input: String?): Boolean {
        if (input.isNullOrEmpty()) return true
        val bpPattern = Regex("^\\d{2,3}/\\d{2,3}\$")
        if (!input.matches(bpPattern)) return false
        val (systolic, diastolic) = input.split("/").map { it.toInt() }
        return systolic in 60..240 && diastolic in 40..140
    }

    private fun validateTemperature(input: String?): Boolean {
        if (input.isNullOrEmpty()) return true
        val temperature = input.toFloatOrNull()
        return temperature != null && temperature in 35.0..42.0
    }

    private fun validateHeartRate(input: String?): Boolean {
        if (input.isNullOrEmpty()) return true
        return input.toIntOrNull() in 40..200
    }

    private fun validateSteps(input: String?): Boolean {
        if (input.isNullOrEmpty()) return true
        return input.toIntOrNull() in 0..10000
    }

    private fun validateOxygenSaturation(input: String?): Boolean {
        if (input.isNullOrEmpty()) return true
        return input.toIntOrNull() in 0..100
    }

    private fun validateRespiratoryRate(input: String?): Boolean {
        if (input.isNullOrEmpty()) return true
        val respiratoryRate = input.toFloatOrNull()
        return respiratoryRate != null && respiratoryRate in 0.0..60.0
    }

    private fun validateDistance(input: String?): Boolean {
        if (input.isNullOrEmpty()) return true
        val distance = input.toFloatOrNull()
        return distance != null && distance in 0.0..100000.0
    }

    private fun validateWeight(input: String?): Boolean {
        if (input.isNullOrEmpty()) return true
        val weight = input.toFloatOrNull()
        return weight != null && weight in 0.0..500.0
    }

    private fun validateHeight(input: String?): Boolean {
        if (input.isNullOrEmpty()) return true
        val height = input.toFloatOrNull()
        return height != null && height in 0.0..250.0
    }

    private fun validateCalories(input: String?): Boolean {
        if (input.isNullOrEmpty()) return true
        return input.toIntOrNull() in 0..10000
    }

    private fun validateSleep(input: String?): Boolean {
        if (input.isNullOrEmpty()) return true
        return input.toIntOrNull() in 0..24
    }

    private fun validateBloodSugar(input: String?): Boolean {
        if (input.isNullOrEmpty()) return true
        return input.toIntOrNull() in 0..500
    }
    private fun showConfirmationDialog() {
        val confirmationDialog = ConfirmationDialog(requireContext())
        confirmationDialog.show()

    }
}