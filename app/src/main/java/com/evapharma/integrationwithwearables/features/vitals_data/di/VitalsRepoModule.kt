package com.evapharma.integrationwithwearables.features.vitals_data.di

import com.evapharma.integrationwithwearables.features.vitals_data.data.local.data_source.VitalsLocalDataSource
import com.evapharma.integrationwithwearables.features.vitals_data.data.local.data_source.VitalsLocalDataSourceImpl
import com.evapharma.integrationwithwearables.features.vitals_data.data.remote.data_source.VitalsRemoteDataSource
import com.evapharma.integrationwithwearables.features.vitals_data.data.remote.data_source.VitalsDataSourceImpl
import com.evapharma.integrationwithwearables.features.vitals_data.data.repo.VitalsRepoImpl
import com.evapharma.integrationwithwearables.features.vitals_data.domain.repo_contract.VitalsRepo
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
abstract class VitalsRepoModule {

    @Binds
    abstract fun bindCovidRemoteDataSourceImpl(
        covidRemoteDataSourceImpl: VitalsDataSourceImpl
    ): VitalsRemoteDataSource

    @Binds
    abstract fun bindCovidRepoImpl(covidRepoImpl: VitalsRepoImpl): VitalsRepo

    @Binds
    abstract fun bindHealthyLocalDataSourceImpl(healthyLocalDataSourceImpl: VitalsLocalDataSourceImpl): VitalsLocalDataSource



}