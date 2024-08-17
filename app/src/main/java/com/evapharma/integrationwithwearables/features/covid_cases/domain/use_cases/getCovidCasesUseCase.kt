package com.evapharma.integrationwithwearables.features.covid_cases.domain.use_cases

import android.content.Context
import com.evapharma.integrationwithwearables.features.covid_cases.domain.repo_contract.CovidRepo
import javax.inject.Inject

class GetCovidCasesUseCase @Inject constructor(private val covidRepo: CovidRepo) {

    suspend operator fun invoke() = covidRepo.getCovidCases()
    suspend  fun checkHealthConnectInstituted(context: Context) = covidRepo.checkHealthConnectInstituted(context)
    suspend  fun checkHealthPermissions() = covidRepo.checkHealthPermissions()
}