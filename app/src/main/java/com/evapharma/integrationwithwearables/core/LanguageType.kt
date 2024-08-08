package com.evapharma.integrationwithwearables.core

import androidx.annotation.StringDef
import com.evapharma.integrationwithwearables.core.LanguageType.Companion.ARABIC
import com.evapharma.integrationwithwearables.core.LanguageType.Companion.ENGLISH


@kotlin.annotation.Retention(AnnotationRetention.SOURCE)
@StringDef(ENGLISH, ARABIC)
annotation class LanguageType {

    companion object {
        const val ENGLISH = "en"
        const val ARABIC = "ar"
    }
}