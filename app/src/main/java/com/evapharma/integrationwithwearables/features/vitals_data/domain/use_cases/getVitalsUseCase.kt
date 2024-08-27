package com.evapharma.integrationwithwearables.features.vitals_data.domain.use_cases

import com.evapharma.integrationwithwearables.features.vitals_data.data.remote.model.NewVitalsRequest
import com.evapharma.integrationwithwearables.features.vitals_data.data.remote.model.VitalsData
import com.evapharma.integrationwithwearables.features.vitals_data.domain.repo_contract.VitalsRepo
import javax.inject.Inject

class GetVitalsUseCase @Inject constructor(private val vitalsRepo: VitalsRepo) {

    suspend operator fun invoke() = vitalsRepo.getVitalsCases()
    suspend fun addVitals(vitals: NewVitalsRequest) =vitalsRepo.addVitals(vitals)
    suspend fun readVitalsData () = vitalsRepo.getVitalsData()
    suspend fun getAllVitals() = vitalsRepo.getAllVitals()

}