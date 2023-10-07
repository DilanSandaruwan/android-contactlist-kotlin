package com.dilan.example.android.ctcontactlistapplication

import android.app.Application
import com.dilan.example.android.ctcontactlistapplication.local.ContactDatabase

class ContactApp : Application() {
    companion object {
        lateinit var contactDatabase: ContactDatabase
    }

    override fun onCreate() {
        super.onCreate()
        contactDatabase = ContactDatabase.getInstance(applicationContext)
    }
}