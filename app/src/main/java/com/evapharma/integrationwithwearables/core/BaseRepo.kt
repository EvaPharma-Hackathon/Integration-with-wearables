package com.evapharma.integrationwithwearables.core

import com.evapharma.integrationwithwearables.core.network.NetworkManager
import javax.inject.Inject

open class BaseRepo {

    @Inject
    lateinit var networkManager: NetworkManager
}