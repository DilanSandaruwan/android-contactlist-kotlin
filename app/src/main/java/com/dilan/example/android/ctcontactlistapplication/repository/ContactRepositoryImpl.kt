package com.dilan.example.android.ctcontactlistapplication.repository

import android.util.Log
import com.dilan.example.android.ctcontactlistapplication.local.ContactDao
import com.dilan.example.android.ctcontactlistapplication.model.ContactData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ContactRepositoryImpl(
    private val contactDao: ContactDao
) : ContactRepository {

    override suspend fun getContacts(): List<ContactData> {
        var contacts: List<ContactData>
        return withContext(Dispatchers.IO) {
            return@withContext getContactsFromLocalDb()
        }
    }

    override suspend fun insertContact(contactData: ContactData): String {
        return withContext(Dispatchers.IO) {
            return@withContext addContactToLocalDb(contactData)
        }
    }

    override suspend fun updateContact(contactData: ContactData): String {
        return withContext(Dispatchers.IO) {
            return@withContext updateContactFromLocalDb(contactData)
        }
    }

    override suspend fun deleteContact(contactData: ContactData): String {
        return withContext(Dispatchers.IO) {
            return@withContext deleteContactFromLocalDb(contactData)
        }
    }

    private suspend fun getContactsFromLocalDb(): List<ContactData> {
        var contacts = contactDao.getAllContacts()
        Log.e(
            "GET",
            "getContactsFromLocalDb: Local contact data fetching SUCCESSFUL contact size: ${contacts.size}"
        )
        return contacts
    }

    private suspend fun addContactToLocalDb(contactData: ContactData): String {
        return try {
            val rowsAffected = contactDao.insertContact(contactData)
            if (rowsAffected > 0) {
                Log.e("ADD", "addContactToLocalDb: $rowsAffected SUCCEEDED")
                "succeeded"
            } else {
                Log.e("ADD", "addContactToLocalDb: $rowsAffected FAILED")
                "failed"
            }
        } catch (e: Exception) {
            Log.e("ADD", "addContactToLocalDb: EXCEPTION")
            e.message.toString()
        }
    }

    private suspend fun deleteContactFromLocalDb(contactData: ContactData): String {
        return try {
            val noOfRowsDeleted = contactDao.deleteContact(contactData)
            if (noOfRowsDeleted > 0) {
                Log.e("DELETE", "deleteContactFromLocalDb: SUCCEEDED")
                "succeeded"
            } else {
                Log.e("DELETE", "deleteContactFromLocalDb: FAILED")
                "failed"
            }
        } catch (e: Exception) {
            Log.e("DELETE", "deleteContactFromLocalDb: EXCEPTION")
            e.message.toString()
        }
    }

    private suspend fun updateContactFromLocalDb(contactData: ContactData): String {

        return try {
            val noOfRowsUpdated = contactDao.updateContact(contactData)
            if (noOfRowsUpdated > 0) {
                Log.e("UPDATE", "updateContactFromLocalDb: SUCCEEDED")
                "succeeded"
            } else {
                Log.e("UPDATE", "updateContactFromLocalDb: FAILED")
                "failed"
            }
        } catch (e: Exception) {
            Log.e("UPDATE", "updateContactFromLocalDb: EXCEPTION")
            e.message.toString()
        }

    }
}