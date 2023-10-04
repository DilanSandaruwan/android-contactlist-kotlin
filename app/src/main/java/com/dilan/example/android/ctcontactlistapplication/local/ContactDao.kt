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
    suspend fun insertUser(contactData: ContactData): Long

    @Query("SELECT * FROM contact_table")
    suspend fun getAllUsers(): List<ContactData>

    @Update
    suspend fun updateUser(user: ContactData)

    @Delete
    suspend fun deleteUser(user: ContactData)

    @Query("DELETE FROM contact_table")
    suspend fun deleteAllUsers()
}