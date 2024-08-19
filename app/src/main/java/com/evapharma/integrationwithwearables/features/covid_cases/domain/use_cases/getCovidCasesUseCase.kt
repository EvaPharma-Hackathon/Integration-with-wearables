package com.evapharma.integrationwithwearables.features.covid_cases.domain.use_cases

import com.evapharma.integrationwithwearables.features.covid_cases.domain.repo_contract.CovidRepo
import javax.inject.Inject

class GetCovidCasesUseCase @Inject constructor(private val covidRepo: CovidRepo) {

    suspend operator fun invoke() = covidRepo.getCovidCases()
    suspend  fun readStepsData(interval: Long) = covidRepo.readStepsData(interval)
    suspend fun readCaloriesData(interval: Long) = covidRepo.readCaloriesData(interval)
    suspend fun readSleepData(interval: Long) = covidRepo.readSleepData(interval)
    suspend fun readDistanceData(interval: Long)=covidRepo.readDistanceData(interval)
    suspend fun readBloodSugarData(interval: Long)=covidRepo.readBloodSugarData(interval)
    suspend fun readOxygenSaturationData(interval: Long)=covidRepo.readOxygenData(interval)
    suspend fun readHeartRateData(interval: Long)=covidRepo.readHeartRateData(interval)
    suspend fun readWeightData(interval: Long)=covidRepo.readWeightData(interval)
    suspend fun readHeightData(interval: Long)=covidRepo.readHeightData(interval)
    suspend fun readBodyTemperatureData(interval: Long)=covidRepo.readBodyTemperatureData(interval)
    suspend fun readBloodPressureData(interval: Long)=covidRepo.readBloodPressureData(interval)
    suspend fun readRespiratoryRateData(interval: Long)=covidRepo.readRespiratoryRateData(interval)

}