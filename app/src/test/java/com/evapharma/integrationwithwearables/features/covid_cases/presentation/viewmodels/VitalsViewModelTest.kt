package com.evapharma.integrationwithwearables.features.covid_cases.presentation.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.evapharma.integrationwithwearables.features.covid_cases.data.local.model.DataType
import com.evapharma.integrationwithwearables.features.covid_cases.data.local.model.VitalsRecord
import com.evapharma.integrationwithwearables.features.covid_cases.data.remote.model.VitalsData
import com.evapharma.integrationwithwearables.features.covid_cases.data.repo.CovidRepoImpl
import com.evapharma.integrationwithwearables.features.covid_cases.domain.repo_contract.CovidRepo
import com.evapharma.integrationwithwearables.features.covid_cases.domain.use_cases.GetCovidCasesUseCase
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

    private lateinit var getCovidCasesUseCase: GetCovidCasesUseCase
    private lateinit var viewModel: VitalsViewModel
    private val testDispatcher = TestCoroutineDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        MockitoAnnotations.openMocks(this)
        val mockRepo = mock(CovidRepo::class.java)
        getCovidCasesUseCase = GetCovidCasesUseCase(mockRepo)
        viewModel = VitalsViewModel(getCovidCasesUseCase)

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
        `when`(getCovidCasesUseCase.readStepsData(1)).thenReturn(listOf(mockVitals))
        `when`(getCovidCasesUseCase.readCaloriesData(1)).thenReturn(listOf(mockVitals))
        `when`(getCovidCasesUseCase.readSleepData(1)).thenReturn(listOf(mockVitals))
        `when`(getCovidCasesUseCase.readDistanceData(1)).thenReturn(listOf(mockVitals))
        `when`(getCovidCasesUseCase.readBloodSugarData(1)).thenReturn(listOf(mockVitals))
        `when`(getCovidCasesUseCase.readOxygenSaturationData(1)).thenReturn(listOf(mockVitals))
        `when`(getCovidCasesUseCase.readHeartRateData(1)).thenReturn(listOf(mockVitals))
        `when`(getCovidCasesUseCase.readWeightData(1)).thenReturn(listOf(mockVitals))
        `when`(getCovidCasesUseCase.readHeightData(1)).thenReturn(listOf(mockVitals))
        `when`(getCovidCasesUseCase.readBodyTemperatureData(1)).thenReturn(listOf(mockVitals))
        `when`(getCovidCasesUseCase.readBloodPressureData(1)).thenReturn(listOf(mockVitals))
        `when`(getCovidCasesUseCase.readRespiratoryRateData(1)).thenReturn(listOf(mockVitals))

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
