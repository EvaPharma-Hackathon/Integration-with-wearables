package com.evapharma.integrationwithwearables.features.vitals_data.domain.use_cases

import com.evapharma.integrationwithwearables.features.vitals_data.data.remote.model.NewVitalsRequest
import com.evapharma.integrationwithwearables.features.vitals_data.domain.repo_contract.VitalsRepo
import javax.inject.Inject

class GetVitalsUseCase @Inject constructor(private val vitalsRepo: VitalsRepo) {

    suspend operator fun invoke() = vitalsRepo.getVitalsCases()
    suspend  fun readStepsData(interval: Long) = vitalsRepo.readStepsData(interval)
    suspend fun readCaloriesData(interval: Long) = vitalsRepo.readCaloriesData(interval)
    suspend fun readSleepData(interval: Long) = vitalsRepo.readSleepData(interval)
    suspend fun readDistanceData(interval: Long)=vitalsRepo.readDistanceData(interval)
    suspend fun readBloodSugarData(interval: Long)=vitalsRepo.readBloodSugarData(interval)
    suspend fun readOxygenSaturationData(interval: Long)=vitalsRepo.readOxygenData(interval)
    suspend fun readHeartRateData(interval: Long)=vitalsRepo.readHeartRateData(interval)
    suspend fun readWeightData(interval: Long)=vitalsRepo.readWeightData(interval)
    suspend fun readHeightData(interval: Long)=vitalsRepo.readHeightData(interval)
    suspend fun readBodyTemperatureData(interval: Long)=vitalsRepo.readBodyTemperatureData(interval)
    suspend fun readBloodPressureData(interval: Long)=vitalsRepo.readBloodPressureData(interval)
    suspend fun readRespiratoryRateData(interval: Long)=vitalsRepo.readRespiratoryRateData(interval)
    suspend fun addVitals(vitals: NewVitalsRequest) =vitalsRepo.addVitals(vitals)

}