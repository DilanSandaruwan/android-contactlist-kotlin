package com.dilan.example.android.ctcontactlistapplication.model

import java.io.Serializable

data class ContactData(
    var firstName: String?,
    var lastName: String?,
    var phoneNumber1: String?,
    var email: String?,
) : Serializable
