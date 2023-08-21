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

    lateinit var binding: ActivityContactListBinding
    private val viewModel: ContactListViewModel by viewModels()
    lateinit var contactList: ArrayList<ContactData>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_contact_list)

        binding.btnArrowBack.setOnClickListener {
            startActivity(Intent(this@ContactListActivity, MainActivity::class.java))
            this.finish()
        }
        val list = initiateContactsList()
        viewModel.setContactList(list)

    }

    private fun initiateContactsList(): ArrayList<ContactData> {
        contactList = ArrayList()
        contactList.add(
            ContactData(
                "Julee",
                "Carnier",
                "+94 862671438",
                "jcarnier0@shutterfly.com"
            )
        )
        contactList.add(
            ContactData(
                "Natalie",
                "Graham",
                "+94 862671423",
                "ngrahm10@shutterfly.com"
            )
        )
//        contactList.add(ContactData("Julee","Carnier","+94 862671438","jcarnier0@shutterfly.com"))
//        contactList.add(ContactData("Julee","Carnier","+94 862671438","jcarnier0@shutterfly.com"))
//        contactList.add(ContactData("Julee","Carnier","+94 862671438","jcarnier0@shutterfly.com"))
//        contactList.add(ContactData("Julee","Carnier","+94 862671438","jcarnier0@shutterfly.com"))
//        contactList.add(ContactData("Julee","Carnier","+94 862671438","jcarnier0@shutterfly.com"))
//        contactList.add(ContactData("Julee","Carnier","+94 862671438","jcarnier0@shutterfly.com"))
//        contactList.add(ContactData("Julee","Carnier","+94 862671438","jcarnier0@shutterfly.com"))
//        contactList.add(ContactData("Julee","Carnier","+94 862671438","jcarnier0@shutterfly.com"))

        return contactList
    }
}