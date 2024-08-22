package com.evapharma.integrationwithwearables.features.vitals_data.presentation.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.evapharma.integrationwithwearables.features.vitals_data.data.local.model.DataType
import com.evapharma.integrationwithwearables.features.vitals_data.data.local.model.VitalsRecord
import com.evapharma.integrationwithwearables.features.vitals_data.data.remote.model.VitalsData
import com.evapharma.integrationwithwearables.features.vitals_data.domain.repo_contract.VitalsRepo
import com.evapharma.integrationwithwearables.features.vitals_data.domain.use_cases.GetVitalsUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

@RunWith(JUnit4::class)
@ExperimentalCoroutinesApi
class VitalsViewModelTest {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    private lateinit var getVitalsUseCase: GetVitalsUseCase
    private lateinit var viewModel: VitalsViewModel
    private val testDispatcher = TestCoroutineDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        MockitoAnnotations.openMocks(this)
        val mockRepo = mock(VitalsRepo::class.java)
        getVitalsUseCase = GetVitalsUseCase(mockRepo)
        viewModel = VitalsViewModel(getVitalsUseCase)

    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }

    @Test
    fun `fetchHealthData updates vitalsData correctly`() = runTest(testDispatcher) {
        // Given
        val mockVitals = VitalsRecord("1000", DataType.STEPS, "2024-08-22T00:00:00Z", "2024-08-21T00:00:00Z")
        `when`(getVitalsUseCase.readStepsData(1)).thenReturn(listOf(mockVitals))
        `when`(getVitalsUseCase.readCaloriesData(1)).thenReturn(listOf(mockVitals))
        `when`(getVitalsUseCase.readSleepData(1)).thenReturn(listOf(mockVitals))
        `when`(getVitalsUseCase.readDistanceData(1)).thenReturn(listOf(mockVitals))
        `when`(getVitalsUseCase.readBloodSugarData(1)).thenReturn(listOf(mockVitals))
        `when`(getVitalsUseCase.readOxygenSaturationData(1)).thenReturn(listOf(mockVitals))
        `when`(getVitalsUseCase.readHeartRateData(1)).thenReturn(listOf(mockVitals))
        `when`(getVitalsUseCase.readWeightData(1)).thenReturn(listOf(mockVitals))
        `when`(getVitalsUseCase.readHeightData(1)).thenReturn(listOf(mockVitals))
        `when`(getVitalsUseCase.readBodyTemperatureData(1)).thenReturn(listOf(mockVitals))
        `when`(getVitalsUseCase.readBloodPressureData(1)).thenReturn(listOf(mockVitals))
        `when`(getVitalsUseCase.readRespiratoryRateData(1)).thenReturn(listOf(mockVitals))

        // When
        viewModel.fetchHealthData()

        // Then
        val expectedVitalsData = VitalsData(
            steps = "1000",
            calories = "1000",
            sleep = "1000",
            distance = "1000",
            bloodSugar = "1000",
            oxygenSaturation = "1000",
            heartRate = "1000",
            weight = "1000",
            height = "1000",
            temperature = "1000",
            bloodPressure = "1000",
            respiratoryRate = "1000"
        )
        assertEquals(expectedVitalsData, viewModel.vitalsData.value)
    }
}
