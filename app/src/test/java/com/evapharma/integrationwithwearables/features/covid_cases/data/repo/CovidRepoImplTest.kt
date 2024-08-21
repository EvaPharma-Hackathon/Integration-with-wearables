package com.evapharma.integrationwithwearables.features.covid_cases.data.repo

import com.evapharma.integrationwithwearables.core.models.DataState
import com.evapharma.integrationwithwearables.features.covid_cases.data.local.data_source.HealthyLocalDataSource
import com.evapharma.integrationwithwearables.features.covid_cases.data.local.model.VitalsRecord
import com.evapharma.integrationwithwearables.features.covid_cases.data.remote.data_source.CovidCasesRemoteDataSource
import com.evapharma.integrationwithwearables.features.covid_cases.data.remote.model.CovidCasesResponse
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class CovidRepoImplTest {

    @Mock
    private lateinit var covidRemoteDataSource: CovidCasesRemoteDataSource

    @Mock
    private lateinit var healthyLocalDataSource: HealthyLocalDataSource

    private lateinit var covidRepoImpl: CovidRepoImpl

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        covidRepoImpl = CovidRepoImpl(covidRemoteDataSource, healthyLocalDataSource)
    }

    @Test
    fun `getCovidCases should return CovidCasesResponse`() = runTest {
        val mockResponse = mock(CovidCasesResponse::class.java)
        `when`(covidRemoteDataSource.getCovidCases()).thenReturn(
            DataState.Success(mockResponse)
        )
        val result = covidRepoImpl.getCovidCases()

        assert(result is DataState.Success)
        val data = (result as DataState.Success).data

        assertEquals(mockResponse, data)
        verify(covidRemoteDataSource).getCovidCases()
    }

    @Test
    fun `readStepsData should return List of VitalsRecord`() = runTest {
        val mockData = listOf(mock(VitalsRecord::class.java))
        `when`(healthyLocalDataSource.readStepsData(1000)).thenReturn(mockData)

        val result = covidRepoImpl.readStepsData(1000)

        assertEquals(mockData, result)
        verify(healthyLocalDataSource).readStepsData(1000)
    }

    @Test
    fun `readCaloriesData should return List of VitalsRecord`() = runTest {
        val mockData = listOf(mock(VitalsRecord::class.java))
        `when`(healthyLocalDataSource.readCaloriesData(1000)).thenReturn(mockData)

        val result = covidRepoImpl.readCaloriesData(1000)

        assertEquals(mockData, result)
        verify(healthyLocalDataSource).readCaloriesData(1000)
    }

    @Test
    fun `readSleepData should return List of VitalsRecord`() = runTest {
        val mockData = listOf(mock(VitalsRecord::class.java))
        `when`(healthyLocalDataSource.readSleepData(1000)).thenReturn(mockData)

        val result = covidRepoImpl.readSleepData(1000)

        assertEquals(mockData, result)
        verify(healthyLocalDataSource).readSleepData(1000)
    }

    @Test
    fun `readDistanceData should return List of VitalsRecord`() = runTest {
        val mockData = listOf(mock(VitalsRecord::class.java))
        `when`(healthyLocalDataSource.readDistanceData(1000)).thenReturn(mockData)

        val result = covidRepoImpl.readDistanceData(1000)

        assertEquals(mockData, result)
        verify(healthyLocalDataSource).readDistanceData(1000)
    }

    @Test
    fun `readBloodSugarData should return List of VitalsRecord`() = runTest {
        val mockData = listOf(mock(VitalsRecord::class.java))
        `when`(healthyLocalDataSource.readBloodSugarData(1000)).thenReturn(mockData)

        val result = covidRepoImpl.readBloodSugarData(1000)

        assertEquals(mockData, result)
        verify(healthyLocalDataSource).readBloodSugarData(1000)
    }

    @Test
    fun `readOxygenData should return List of VitalsRecord`() = runTest {
        val mockData = listOf(mock(VitalsRecord::class.java))
        `when`(healthyLocalDataSource.readOxygenSaturationData(1000)).thenReturn(mockData)

        val result = covidRepoImpl.readOxygenData(1000)

        assertEquals(mockData, result)
        verify(healthyLocalDataSource).readOxygenSaturationData(1000)
    }

    @Test
    fun `readHeartRateData should return List of VitalsRecord`() = runTest {
        val mockData = listOf(mock(VitalsRecord::class.java))
        `when`(healthyLocalDataSource.readHeartRateData(1000)).thenReturn(mockData)

        val result = covidRepoImpl.readHeartRateData(1000)

        assertEquals(mockData, result)
        verify(healthyLocalDataSource).readHeartRateData(1000)
    }

    @Test
    fun `readWeightData should return List of VitalsRecord`() = runTest {
        val mockData = listOf(mock(VitalsRecord::class.java))
        `when`(healthyLocalDataSource.readWeightData(1000)).thenReturn(mockData)

        val result = covidRepoImpl.readWeightData(1000)

        assertEquals(mockData, result)
        verify(healthyLocalDataSource).readWeightData(1000)
    }

    @Test
    fun `readHeightData should return List of VitalsRecord`() = runTest {
        val mockData = listOf(mock(VitalsRecord::class.java))
        `when`(healthyLocalDataSource.readHeightData(1000)).thenReturn(mockData)

        val result = covidRepoImpl.readHeightData(1000)

        assertEquals(mockData, result)
        verify(healthyLocalDataSource).readHeightData(1000)
    }

    @Test
    fun `readBodyTemperatureData should return List of VitalsRecord`() = runTest {
        val mockData = listOf(mock(VitalsRecord::class.java))
        `when`(healthyLocalDataSource.readBodyTemperatureData(1000)).thenReturn(mockData)

        val result = covidRepoImpl.readBodyTemperatureData(1000)

        assertEquals(mockData, result)
        verify(healthyLocalDataSource).readBodyTemperatureData(1000)
    }

    @Test
    fun `readBloodPressureData should return List of VitalsRecord`() = runTest {
        val mockData = listOf(mock(VitalsRecord::class.java))
        `when`(healthyLocalDataSource.readBloodPressureData(1000)).thenReturn(mockData)

        val result = covidRepoImpl.readBloodPressureData(1000)

        assertEquals(mockData, result)
        verify(healthyLocalDataSource).readBloodPressureData(1000)
    }
}