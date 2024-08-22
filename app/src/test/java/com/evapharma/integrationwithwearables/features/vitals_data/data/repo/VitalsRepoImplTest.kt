package com.evapharma.integrationwithwearables.features.vitals_data.data.repo

import com.evapharma.integrationwithwearables.core.models.DataState
import com.evapharma.integrationwithwearables.features.vitals_data.data.local.data_source.VitalsLocalDataSource
import com.evapharma.integrationwithwearables.features.vitals_data.data.local.model.VitalsRecord
import com.evapharma.integrationwithwearables.features.vitals_data.data.remote.data_source.VitalsRemoteDataSource
import com.evapharma.integrationwithwearables.features.vitals_data.data.remote.model.VitalsCaseResponse
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
class VitalsRepoImplTest {

    @Mock
    private lateinit var vitalsRemoteDataSource: VitalsRemoteDataSource

    @Mock
    private lateinit var vitalsLocalDataSource: VitalsLocalDataSource

    private lateinit var vitalsRepoImpl: VitalsRepoImpl

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        vitalsRepoImpl = VitalsRepoImpl(vitalsRemoteDataSource, vitalsLocalDataSource)
    }

    @Test
    fun `getVitals should return vitalsCasesResponse`() = runTest {
        val mockResponse = mock(VitalsCaseResponse::class.java)
        `when`(vitalsRemoteDataSource.getVitalsCases()).thenReturn(
            DataState.Success(mockResponse)
        )
        val result = vitalsRepoImpl.getVitalsCases()

        assert(result is DataState.Success)
        val data = (result as DataState.Success).data

        assertEquals(mockResponse, data)
        verify(vitalsRemoteDataSource).getVitalsCases()
    }

    @Test
    fun `readStepsData should return List of VitalsRecord`() = runTest {
        val mockData = listOf(mock(VitalsRecord::class.java))
        `when`(vitalsLocalDataSource.readStepsData(1000)).thenReturn(mockData)

        val result = vitalsRepoImpl.readStepsData(1000)

        assertEquals(mockData, result)
        verify(vitalsLocalDataSource).readStepsData(1000)
    }

    @Test
    fun `readCaloriesData should return List of VitalsRecord`() = runTest {
        val mockData = listOf(mock(VitalsRecord::class.java))
        `when`(vitalsLocalDataSource.readCaloriesData(1000)).thenReturn(mockData)

        val result = vitalsRepoImpl.readCaloriesData(1000)

        assertEquals(mockData, result)
        verify(vitalsLocalDataSource).readCaloriesData(1000)
    }

    @Test
    fun `readSleepData should return List of VitalsRecord`() = runTest {
        val mockData = listOf(mock(VitalsRecord::class.java))
        `when`(vitalsLocalDataSource.readSleepData(1000)).thenReturn(mockData)

        val result = vitalsRepoImpl.readSleepData(1000)

        assertEquals(mockData, result)
        verify(vitalsLocalDataSource).readSleepData(1000)
    }

    @Test
    fun `readDistanceData should return List of VitalsRecord`() = runTest {
        val mockData = listOf(mock(VitalsRecord::class.java))
        `when`(vitalsLocalDataSource.readDistanceData(1000)).thenReturn(mockData)

        val result = vitalsRepoImpl.readDistanceData(1000)

        assertEquals(mockData, result)
        verify(vitalsLocalDataSource).readDistanceData(1000)
    }

    @Test
    fun `readBloodSugarData should return List of VitalsRecord`() = runTest {
        val mockData = listOf(mock(VitalsRecord::class.java))
        `when`(vitalsLocalDataSource.readBloodSugarData(1000)).thenReturn(mockData)

        val result = vitalsRepoImpl.readBloodSugarData(1000)

        assertEquals(mockData, result)
        verify(vitalsLocalDataSource).readBloodSugarData(1000)
    }

    @Test
    fun `readOxygenData should return List of VitalsRecord`() = runTest {
        val mockData = listOf(mock(VitalsRecord::class.java))
        `when`(vitalsLocalDataSource.readOxygenSaturationData(1000)).thenReturn(mockData)

        val result = vitalsRepoImpl.readOxygenData(1000)

        assertEquals(mockData, result)
        verify(vitalsLocalDataSource).readOxygenSaturationData(1000)
    }

    @Test
    fun `readHeartRateData should return List of VitalsRecord`() = runTest {
        val mockData = listOf(mock(VitalsRecord::class.java))
        `when`(vitalsLocalDataSource.readHeartRateData(1000)).thenReturn(mockData)

        val result = vitalsRepoImpl.readHeartRateData(1000)

        assertEquals(mockData, result)
        verify(vitalsLocalDataSource).readHeartRateData(1000)
    }

    @Test
    fun `readWeightData should return List of VitalsRecord`() = runTest {
        val mockData = listOf(mock(VitalsRecord::class.java))
        `when`(vitalsLocalDataSource.readWeightData(1000)).thenReturn(mockData)

        val result = vitalsRepoImpl.readWeightData(1000)

        assertEquals(mockData, result)
        verify(vitalsLocalDataSource).readWeightData(1000)
    }

    @Test
    fun `readHeightData should return List of VitalsRecord`() = runTest {
        val mockData = listOf(mock(VitalsRecord::class.java))
        `when`(vitalsLocalDataSource.readHeightData(1000)).thenReturn(mockData)

        val result = vitalsRepoImpl.readHeightData(1000)

        assertEquals(mockData, result)
        verify(vitalsLocalDataSource).readHeightData(1000)
    }

    @Test
    fun `readBodyTemperatureData should return List of VitalsRecord`() = runTest {
        val mockData = listOf(mock(VitalsRecord::class.java))
        `when`(vitalsLocalDataSource.readBodyTemperatureData(1000)).thenReturn(mockData)

        val result = vitalsRepoImpl.readBodyTemperatureData(1000)

        assertEquals(mockData, result)
        verify(vitalsLocalDataSource).readBodyTemperatureData(1000)
    }

    @Test
    fun `readBloodPressureData should return List of VitalsRecord`() = runTest {
        val mockData = listOf(mock(VitalsRecord::class.java))
        `when`(vitalsLocalDataSource.readBloodPressureData(1000)).thenReturn(mockData)

        val result = vitalsRepoImpl.readBloodPressureData(1000)

        assertEquals(mockData, result)
        verify(vitalsLocalDataSource).readBloodPressureData(1000)
    }
}