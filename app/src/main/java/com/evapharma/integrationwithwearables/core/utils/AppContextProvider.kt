package com.evapharma.integrationwithwearables.core.utils

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class AppContextProvider @Inject constructor(@ApplicationContext context: Context) {
    private val ctx = context

    fun getAppContext(): Context {
        return ctx
    }

}