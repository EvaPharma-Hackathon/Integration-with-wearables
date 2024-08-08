package com.evapharma.integrationwithwearables.core.utils

import androidx.annotation.StringRes
import com.evapharma.integrationwithwearables.core.LanguageType
import com.evapharma.integrationwithwearables.core.LocaleHelper
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StringLocale @Inject constructor() {

    @Inject
    lateinit var appContextProvider: AppContextProvider

    fun getLanguage(): String {
        return LocaleHelper.getLanguage(appContextProvider.getAppContext()) ?: LanguageType.ARABIC
    }

    fun localize(@StringRes resId: Int): String {
        try {
            return appContextProvider.getAppContext().resources.getString(resId)
        } catch (e: Exception) {
            e.printStackTrace()
            println(e.toString())
        }
        return Constants.EMPTY_STRING
    }

    companion object {
        lateinit var instance: StringLocale
    }
}