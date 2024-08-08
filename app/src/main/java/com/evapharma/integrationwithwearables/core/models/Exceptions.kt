package com.evapharma.integrationwithwearables.core.models

sealed class Exceptions {
    data object InternalServerError : Exceptions()
    data object Unauthorized : Exceptions()
    data class ErrorResponse(val msg: String, val code: Int? = null) :
        Exceptions()
}
