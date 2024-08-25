package com.evapharma.integrationwithwearables.features.vitals_data.domain.repo_contract

import com.evapharma.integrationwithwearables.core.models.DataState
import com.evapharma.integrationwithwearables.features.vitals_data.data.local.model.VitalsRecord
import com.evapharma.integrationwithwearables.features.vitals_data.data.remote.model.NewVitalsRequest
import com.evapharma.integrationwithwearables.features.vitals_data.data.remote.model.VitalsCaseResponse

interface VitalsRepo {

    suspend fun getVitalsCases(): DataState<VitalsCaseResponse>
    suspend fun readStepsData(interval: Long): List<VitalsRecord>
    suspend fun readCaloriesData(interval: Long): List<VitalsRecord>
    suspend fun readSleepData(interval: Long): List<VitalsRecord>
    suspend fun readDistanceData(interval: Long): List<VitalsRecord>
    suspend fun readBloodSugarData(interval: Long): List<VitalsRecord>
    suspend fun readOxygenData(interval: Long): List<VitalsRecord>
    suspend fun readHeartRateData(interval: Long): List<VitalsRecord>
    suspend fun readWeightData(interval: Long): List<VitalsRecord>
    suspend fun readHeightData(interval: Long): List<VitalsRecord>
    suspend fun readBodyTemperatureData(interval: Long): List<VitalsRecord>
    suspend fun readBloodPressureData(interval: Long): List<VitalsRecord>
    suspend fun readRespiratoryRateData(interval:Long): List<VitalsRecord>
    suspend fun addVitals(vitals: NewVitalsRequest) : DataState<Int>

}