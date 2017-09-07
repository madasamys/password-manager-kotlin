package com.mcruncher.password

import android.app.Application
import android.content.Context

import io.realm.Realm
import io.realm.RealmConfiguration

/**
 * Author : Madasamy
 * Version : x.x.x
 */

class PasswordApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
        setRealmConfiguration()
    }

    companion object {
        var context: Context? = null
            private set

        fun setRealmConfiguration() {
            Realm.init(context)
            val config = RealmConfiguration.Builder()
                    .schemaVersion(0)
                    .build()
            Realm.setDefaultConfiguration(config)

        }
    }
}
