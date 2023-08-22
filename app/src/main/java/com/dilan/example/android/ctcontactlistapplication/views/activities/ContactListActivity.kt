package com.dilan.example.android.ctcontactlistapplication.views.activities

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.dilan.example.android.ctcontactlistapplication.R
import com.dilan.example.android.ctcontactlistapplication.databinding.ActivityContactListBinding
import com.dilan.example.android.ctcontactlistapplication.model.ContactData
import com.dilan.example.android.ctcontactlistapplication.viewmodels.ContactListViewModel

class ContactListActivity : AppCompatActivity() {

    // Declare lateinit variables
    lateinit var binding: ActivityContactListBinding
    lateinit var contactList: ArrayList<ContactData>

    // Initialize ContactListViewModel using viewModels extension function
    private val viewModel: ContactListViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inflate the layout using DataBinding
        binding = DataBindingUtil.setContentView(this, R.layout.activity_contact_list)

        // Set up click listener for back button
        binding.btnArrowBack.setOnClickListener {
            // Navigate back to the main activity
            startActivity(Intent(this@ContactListActivity, MainActivity::class.java))
            this.finish()
        }

        // Set up click listener for exit button
        binding.btnExit.setOnClickListener {
            // Exit from the app
            this.finish()
        }

        // Initialize and set up the initial contacts list
        val list = initiateContactsList()
        viewModel.setContactList(list)

    }

    /**
     * Create and return an ArrayList of initial contact data.
     */
    private fun initiateContactsList(): ArrayList<ContactData> {
        contactList = ArrayList()

        // Add sample contact data to the list
        contactList.add(
            ContactData(
                "Julee",
                "Carnier",
                "0762671438",
                "jcarnier0@shutterfly.com"
            )
        )
        contactList.add(
            ContactData(
                "Natalie",
                "Graham",
                "0762671423",
                "ngrahm10@shutterfly.com"
            )
        )

        // Return the initialized contact list
        return contactList
    }
}