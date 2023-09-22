package com.dilan.example.android.ctcontactlistapplication.model

import java.io.Serializable

/**
 * Data class representing contact information.
 *
 * @property firstName The first name of the contact.
 * @property lastName The last name of the contact.
 * @property phoneNumber The primary phone number of the contact.
 * @property email The email address of the contact.
 */
data class ContactData(
    var firstName: String?,
    var lastName: String?,
    var phoneNumber: String?,
    var email: String?,
) : Serializable
