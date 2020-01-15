package com.daffodil.renters.application

import android.app.Application

class RentersApplication : Application() {

    companion object {
        lateinit var instance: RentersApplication
            private set
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }


}