package com.evapharma.integrationwithwearables.features.vitals_data.data.repo

import com.evapharma.integrationwithwearables.core.models.DataState
import com.evapharma.integrationwithwearables.features.vitals_data.data.local.data_source.VitalsLocalDataSource
import com.evapharma.integrationwithwearables.features.vitals_data.data.local.model.VitalsRecord
import com.evapharma.integrationwithwearables.features.vitals_data.data.remote.data_source.VitalsRemoteDataSource
import com.evapharma.integrationwithwearables.features.vitals_data.data.remote.model.NewVitalsRequest
import com.evapharma.integrationwithwearables.features.vitals_data.data.remote.model.VitalsCaseResponse
import com.evapharma.integrationwithwearables.features.vitals_data.data.remote.model.VitalsData
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
    fun `addVitals should return success when remote data source returns success`() = runTest {
        val vitalsRequest = mock(NewVitalsRequest::class.java)
        val expectedResult = DataState.Success(1)

        `when`(vitalsRemoteDataSource.addVitals(vitalsRequest)).thenReturn(expectedResult)

        val result = vitalsRepoImpl.addVitals(vitalsRequest)

        assert(result is DataState.Success)
        assertEquals(expectedResult, result)
        verify(vitalsRemoteDataSource).addVitals(vitalsRequest)
    }

    @Test
    fun `getVitalsData should return expected vitalsData`() = runTest {
        val expectedVitalsData = mock(VitalsData::class.java)

        `when`(vitalsLocalDataSource.getVitalsData()).thenReturn(expectedVitalsData)

        val result = vitalsRepoImpl.getVitalsData()

        assertEquals(expectedVitalsData, result)
        verify(vitalsLocalDataSource).getVitalsData()
    }
}