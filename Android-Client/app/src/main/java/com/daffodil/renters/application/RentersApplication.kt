package com.daffodil.renters.application

import android.app.Application

open class RentersApplication : Application() {

    companion object {
        lateinit var instance: RentersApplication
            private set
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

}