package com.evapharma.integrationwithwearables.features.covid_cases.di

import android.content.Context
import androidx.health.connect.client.HealthConnectClient
import com.evapharma.integrationwithwearables.features.covid_cases.data.local.data_source.HealthyLocalDataSource
import com.evapharma.integrationwithwearables.features.covid_cases.data.local.data_source.HealthyLocalDataSourceImpl
import com.evapharma.integrationwithwearables.features.covid_cases.data.remote.data_source.CovidCasesRemoteDataSource
import com.evapharma.integrationwithwearables.features.covid_cases.data.remote.data_source.CovidRemoteDataSourceImpl
import com.evapharma.integrationwithwearables.features.covid_cases.data.repo.CovidRepoImpl
import com.evapharma.integrationwithwearables.features.covid_cases.domain.repo_contract.CovidRepo
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
abstract class CovidRepoModule {

    @Binds
    abstract fun bindCovidRemoteDataSourceImpl(
        covidRemoteDataSourceImpl: CovidRemoteDataSourceImpl
    ): CovidCasesRemoteDataSource

    @Binds
    abstract fun bindCovidRepoImpl(covidRepoImpl: CovidRepoImpl): CovidRepo

    @Binds
    abstract fun bindHealthyLocalDataSourceImpl(healthyLocalDataSourceImpl: HealthyLocalDataSourceImpl): HealthyLocalDataSource



}