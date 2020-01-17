package com.daffodil.renters.application

import android.app.Application
import android.content.Context
import androidx.core.content.edit
import com.daffodil.renters.R

open class RentersApplication : Application() {

    companion object {
        val SHARED_PREFERENCES_KEY = "fwg9gb3m"
        val SERVER_IP_ADDRESS_KEY = "ne98t3"
        val SERVER_PORT_NUMBER_KEY = "seniogh3"

        lateinit var instance: RentersApplication
            private set
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    var serverIpAddress: String
        get() {
            val default = instance.resources.getString(R.string.default_ip_address)
            return instance.getSharedPreferences(
                SHARED_PREFERENCES_KEY,
                Context.MODE_PRIVATE
            ).getString(
                SERVER_IP_ADDRESS_KEY,
                default
            ) ?: default
        }
        set(value) {
            instance.getSharedPreferences(SHARED_PREFERENCES_KEY, Context.MODE_PRIVATE).edit {
                putString(SERVER_IP_ADDRESS_KEY, value)
            }
        }

    var serverPortNumber: String
        get() {
            val default = instance.resources.getString(R.string.default_port_number)
            return instance.getSharedPreferences(
                SHARED_PREFERENCES_KEY,
                Context.MODE_PRIVATE
            ).getString(
                SERVER_PORT_NUMBER_KEY,
                default
            ) ?: default
        }
        set(value) {
            instance.getSharedPreferences(SHARED_PREFERENCES_KEY, Context.MODE_PRIVATE).edit {
                putString(SERVER_PORT_NUMBER_KEY, value)
            }
        }
}