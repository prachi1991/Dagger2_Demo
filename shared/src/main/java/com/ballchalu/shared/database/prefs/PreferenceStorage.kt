package com.ballchalu.shared.database.prefs

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.content.SharedPreferences.OnSharedPreferenceChangeListener
import androidx.annotation.WorkerThread
import androidx.core.content.edit
import com.ballchalu.shared.util.ConstantsBase
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 * Storage for app and user preferences.
 */
interface PreferenceStorage {
    var token: String?
    var userName: String?
    var userEmail: String?
    var theme: String?

}

/**
 * [PreferenceStorage] impl backed by [android.content.SharedPreferences].
 */
@Singleton
class SharedPreferenceStorage @Inject constructor(context: Context) :
    PreferenceStorage {


    private val prefs = context.applicationContext.getSharedPreferences(PREFS_NAME, MODE_PRIVATE)
    override var token by StringPreference(
        prefs,
        PREF_TOKEN,
        ""
    )
    override var userEmail by StringPreference(prefs, PREF_USERNAME, "")

    override var userName by StringPreference(prefs, PREF_USER_EMAIL, "")

    override var theme by StringPreference(prefs, PREF_THEME, ConstantsBase.THEME_DARK_MODE)

    companion object {
        const val PREFS_NAME = "SharePref"
        const val PREF_TOKEN = "pref_token"
        const val PREF_USERNAME = "pref_username"
        const val PREF_USER_EMAIL = "pref_user_email"
        const val PREF_THEME = "pref_theme"
    }

    fun registerOnPreferenceChangeListener(listener: OnSharedPreferenceChangeListener) {
        prefs.registerOnSharedPreferenceChangeListener(listener)
    }

    fun cleanPreferenceStorage() {
        val editor: SharedPreferences.Editor = prefs.edit()

        editor.clear()
        editor.apply()
    }
}

class BooleanPreference(
    private val preferences: SharedPreferences,
    private val name: String,
    private val defaultValue: Boolean
) : ReadWriteProperty<Any, Boolean> {

    @WorkerThread
    override fun getValue(thisRef: Any, property: KProperty<*>): Boolean {
        return preferences.getBoolean(name, defaultValue)
    }

    override fun setValue(thisRef: Any, property: KProperty<*>, value: Boolean) {
        preferences.edit { putBoolean(name, value) }
    }
}

class StringPreference(
    private val preferences: SharedPreferences,
    private val name: String,
    private val defaultValue: String?
) : ReadWriteProperty<Any, String?> {

    @WorkerThread
    override fun getValue(thisRef: Any, property: KProperty<*>): String? {
        return preferences.getString(name, defaultValue)
    }

    override fun setValue(thisRef: Any, property: KProperty<*>, value: String?) {
        preferences.edit { putString(name, value) }
    }
}
