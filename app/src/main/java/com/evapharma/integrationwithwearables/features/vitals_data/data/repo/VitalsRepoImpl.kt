package com.evapharma.integrationwithwearables.features.vitals_data.data.repo

import com.evapharma.integrationwithwearables.core.models.DataState
import com.evapharma.integrationwithwearables.features.vitals_data.data.local.data_source.VitalsLocalDataSource
import com.evapharma.integrationwithwearables.features.vitals_data.data.local.model.VitalsRecord
import com.evapharma.integrationwithwearables.features.vitals_data.data.remote.data_source.VitalsRemoteDataSource
import com.evapharma.integrationwithwearables.features.vitals_data.data.remote.model.VitalsCaseResponse
import com.evapharma.integrationwithwearables.features.vitals_data.domain.repo_contract.VitalsRepo
import javax.inject.Inject

class VitalsRepoImpl @Inject constructor(private val vitalsRemoteDataSourceImpl: VitalsRemoteDataSource, private val vitalsLocalDataSourceImpl: VitalsLocalDataSource):
    VitalsRepo {
    override suspend fun getVitalsCases(): DataState<VitalsCaseResponse> {
        return vitalsRemoteDataSourceImpl.getVitalsCases()
    }

    override suspend fun readStepsData(interval: Long): List<VitalsRecord> {
        return vitalsLocalDataSourceImpl.readStepsData(interval)
    }

    override suspend fun readCaloriesData(interval: Long): List<VitalsRecord> {
        return vitalsLocalDataSourceImpl.readCaloriesData(interval)
    }

    override suspend fun readSleepData(interval: Long): List<VitalsRecord> {
        return vitalsLocalDataSourceImpl.readSleepData(interval)
    }

    override suspend fun readDistanceData(interval: Long): List<VitalsRecord> {
        return vitalsLocalDataSourceImpl.readDistanceData(interval)
    }

    override suspend fun readBloodSugarData(interval: Long): List<VitalsRecord> {
        return vitalsLocalDataSourceImpl.readBloodSugarData(interval)
    }

    override suspend fun readOxygenData(interval: Long): List<VitalsRecord> {
        return vitalsLocalDataSourceImpl.readOxygenSaturationData(interval)
    }

    override suspend fun readHeartRateData(interval: Long): List<VitalsRecord> {
        return vitalsLocalDataSourceImpl.readHeartRateData(interval)
    }

    override suspend fun readWeightData(interval: Long): List<VitalsRecord> {
        return vitalsLocalDataSourceImpl.readWeightData(interval)
    }

    override suspend fun readHeightData(interval: Long): List<VitalsRecord> {
        return vitalsLocalDataSourceImpl.readHeightData(interval)
    }

    override suspend fun readBodyTemperatureData(interval: Long): List<VitalsRecord> {
        return vitalsLocalDataSourceImpl.readBodyTemperatureData(interval)
    }

    override suspend fun readBloodPressureData(interval: Long): List<VitalsRecord> {
       return vitalsLocalDataSourceImpl.readBloodPressureData(interval)
    }

    override suspend fun readRespiratoryRateData(interval: Long): List<VitalsRecord> {
       return vitalsLocalDataSourceImpl.readRespiratoryRate(interval)
    }


}