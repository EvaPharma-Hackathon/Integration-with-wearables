package com.evapharma.integrationwithwearables.core

import android.content.Context
import android.content.res.Configuration
import android.os.Build
import android.os.LocaleList
import androidx.preference.PreferenceManager
import java.util.*

object LocaleHelper {

    private const val SELECTED_LANGUAGE = "Locale.Helper.Selected.Language"

    fun getNewLanguage(context: Context?): String =
        if (getLanguage(context).equals(LanguageType.ARABIC)) LanguageType.ARABIC
        else LanguageType.ENGLISH

    fun getLanguage(context: Context?): String? {
        return context?.let { getPersistedData(it, getDefault()) }
    }

    private fun getDefault(): String {
        var defaultLang = Locale.getDefault().language
        if (defaultLang != LanguageType.ARABIC && defaultLang != LanguageType.ENGLISH) defaultLang =
            LanguageType.ENGLISH
        return defaultLang
    }

    fun onAttach(context: Context?): Context? {
        return getLanguage(context)?.let { context?.let { it1 -> setLocale(it1, it) } }
    }

    private fun getPersistedData(context: Context, defaultLanguage: String): String? {
        val preferences = PreferenceManager.getDefaultSharedPreferences(context)
        return preferences.getString(SELECTED_LANGUAGE, defaultLanguage)
    }

    private fun persist(context: Context, language: String) {
        val preferences = PreferenceManager.getDefaultSharedPreferences(context)
        val editor = preferences.edit()

        editor.putString(SELECTED_LANGUAGE, language)
        editor.apply()
    }

    fun setLocale(context: Context, lang: String): Context {
        persist(context, lang)

        val locale = Locale(lang)
        val configuration = context.resources.configuration
        val res = context.resources

        Locale.setDefault(locale)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
            configuration.setLocales(LocaleList(locale))
        else {
            configuration.setLocale(locale)
        }

        configuration.setLayoutDirection(locale)

        context.createConfigurationContext(configuration)
        return context
    }

    fun wrapContext(context: Context): Context {

        val savedLocale =
            getLanguage(context) // load the user picked language from persistence (e.g SharedPreferences)
                ?: return context // else return the original untouched context

        // as part of creating a new context that contains the new locale we also need to override the default locale.
        Locale.setDefault(Locale(savedLocale))

        // create new configuration with the saved locale
        val newConfig = Configuration()
        newConfig.setLocale(Locale(savedLocale))

        return context.createConfigurationContext(newConfig)
    }

    fun overrideLocale(context: Context) {
        val savedLocale = getLanguage(context) ?: return

        // as part of creating a new context that contains the new locale we also need to override the default locale.
        Locale.setDefault(Locale(savedLocale))

        // create new configuration with the saved locale
        val newConfig = Configuration()
        newConfig.setLocale(Locale(savedLocale))

        // override the locale on the given context (Activity, Fragment, etc...)
        context.resources.updateConfiguration(newConfig, context.resources.displayMetrics)

        if (context != context.applicationContext) {
            context.applicationContext.resources.run {
                updateConfiguration(newConfig, displayMetrics)
            }
        }
    }

}