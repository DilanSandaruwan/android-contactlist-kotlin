package com.dilan.example.android.ctcontactlistapplication.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.dilan.example.android.ctcontactlistapplication.model.ContactData

@Dao
interface ContactDao {
    @Insert
    suspend fun insertContact(contactData: ContactData): Long

    @Query("SELECT * FROM contact_table")
    suspend fun getAllContacts(): List<ContactData>

    @Update
    suspend fun updateContact(contactData: ContactData): Int

    @Delete
    suspend fun deleteContact(contactData: ContactData): Int

    @Query("DELETE FROM contact_table")
    suspend fun deleteAllContacts()
}