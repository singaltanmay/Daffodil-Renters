package com.daffodil.renters.application

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import com.daffodil.renters.R

open class RentersApplication : Application() {

    companion object {
        val SHARED_PREFERENCES_KEY = /*RentersPrefs*/"com.daffodil.renters_preferences"
        val SERVER_PORT_NUMBER_KEY = "serverPortNumber"
        val USER_ID_KEY = "userID"

        lateinit var serverIpAddressKey: String

        lateinit var instance: RentersApplication
            private set
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        serverIpAddressKey =
            instance.applicationContext.getString(R.string.server_ip_address_key)
    }

    var serverIpAddress: String
        get() {
            val default = instance.resources.getString(R.string.default_ip_address)
            return if (getAppPreferences().contains(serverIpAddressKey)) {
                getAppPreferences().getString(
                    serverIpAddressKey,
                    default
                ) ?: default
            } else {
                serverIpAddress = default
                default
            }
        }
        set(value) {
            getAppPreferences().edit {
                putString(serverIpAddressKey, value)
            }
        }

    var serverPortNumber: String
        get() {
            val default = instance.resources.getString(R.string.default_port_number)
            return if (getAppPreferences().contains(SERVER_PORT_NUMBER_KEY)) {
                getAppPreferences().getString(
                    SERVER_PORT_NUMBER_KEY,
                    default
                ) ?: default
            } else {
                serverPortNumber = default
                default
            }
        }
        set(value) {
            getAppPreferences().edit {
                putString(SERVER_PORT_NUMBER_KEY, value)
            }
        }

    fun getAppPreferences(): SharedPreferences = instance.getSharedPreferences(
        SHARED_PREFERENCES_KEY,
        Context.MODE_PRIVATE
    )

    fun isUserLoggedIn(prefs: SharedPreferences = getAppPreferences()) = prefs.contains(USER_ID_KEY)

    fun getUserId(prefs: SharedPreferences = getAppPreferences()): String? {
        return prefs.getString(USER_ID_KEY, null)
    }

}