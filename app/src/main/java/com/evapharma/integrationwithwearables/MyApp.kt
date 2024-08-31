package com.evapharma.integrationwithwearables

import android.app.Application
import android.content.Context
import android.content.res.Configuration
import com.evapharma.integrationwithwearables.core.LocaleHelper
import com.evapharma.integrationwithwearables.core.utils.StringLocale
import dagger.hilt.android.HiltAndroidApp
import es.dmoral.toasty.Toasty
import javax.inject.Inject

@HiltAndroidApp
class MyApp: Application() {

    @Inject
    lateinit var stringLocale: StringLocale


    companion object {
        private var instance: MyApp? = null
        fun getAppContext(): Context {
            return instance!!.applicationContext
        }

        var shouldCheckInsuranceType = false
        var isDarkMode = false

    }

    override fun onCreate() {
        super.onCreate()
        Toasty.Config.getInstance().allowQueue(false).apply()

        isDarkMode = isDarkMode(this)
        StringLocale.instance = stringLocale
    }

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(LocaleHelper.wrapContext(base))
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        LocaleHelper.overrideLocale(this)
        shouldCheckInsuranceType = true
        isDarkMode = isDarkMode(this)
    }

    private fun isDarkMode(context: Context): Boolean {
        val darkModeFlag =
            context.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
        return darkModeFlag == Configuration.UI_MODE_NIGHT_YES
    }


}