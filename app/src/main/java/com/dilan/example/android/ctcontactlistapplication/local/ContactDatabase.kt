package com.dilan.example.android.ctcontactlistapplication.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.dilan.example.android.ctcontactlistapplication.model.ContactData

@Database(entities = [ContactData::class], version = 1)
abstract class ContactDatabase : RoomDatabase() {
    abstract fun userDao(): ContactDao

    companion object {
        @Volatile
        private var INSTANCE: ContactDatabase? = null
        fun getInstance(context: Context): ContactDatabase {
            return INSTANCE
                ?: synchronized(
                    this
                ) {
                    INSTANCE ?: Room.databaseBuilder(
                        context.applicationContext,
                        ContactDatabase::class .java,
                        "user_database"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                        .also { INSTANCE = it }
                }
        }

    }
}