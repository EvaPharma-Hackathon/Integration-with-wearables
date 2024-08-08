package com.evapharma.integrationwithwearables.core

import com.evapharma.integrationwithwearables.core.models.ErrorResponse

interface BaseView {

    fun onError(error: ErrorResponse?)

    fun showLoading()

    fun hideLoading()

    fun showSuccessMsg(msg: String)

    fun showErrorMsg(msg: String)
}