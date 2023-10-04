package com.dilan.example.android.ctcontactlistapplication.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * Data class representing contact information.
 *
 * @property firstName The first name of the contact.
 * @property lastName The last name of the contact.
 * @property phoneNumber The primary phone number of the contact.
 * @property email The email address of the contact.
 */

@Entity(tableName = "contact_table")
data class ContactData(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    @SerializedName("first_name") // api service used name
    @ColumnInfo(name = "first_name") // db used name
    var firstName: String?,
    @SerializedName("first_name") // api service used name
    @ColumnInfo(name = "last_name") // db used name
    var lastName: String?,
    var phoneNumber: String?,
    var email: String?,
)

/*
data class ContactData(
    var firstName: String?,
    var lastName: String?,
    var phoneNumber: String?,
    var email: String?,
) : Serializable
 */
