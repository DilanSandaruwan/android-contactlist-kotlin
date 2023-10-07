package com.dilan.example.android.ctcontactlistapplication.repository

import com.dilan.example.android.ctcontactlistapplication.model.ContactData

interface ContactRepository {

    suspend fun getContacts(): List<ContactData>
    suspend fun insertContact(contactData: ContactData): String
    suspend fun updateContact(contactData: ContactData): String
    suspend fun deleteContact(contactData: ContactData): String
}