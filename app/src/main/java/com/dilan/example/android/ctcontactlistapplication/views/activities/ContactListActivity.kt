package com.dilan.example.android.ctcontactlistapplication.views.activities

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.dilan.example.android.ctcontactlistapplication.R
import com.dilan.example.android.ctcontactlistapplication.databinding.ActivityContactListBinding
import com.dilan.example.android.ctcontactlistapplication.viewmodels.ContactListViewModel

class ContactListActivity : AppCompatActivity() {

    // Declare lateinit variables
    lateinit var binding: ActivityContactListBinding

    // Initialize ContactListViewModel using viewModels extension function
    private val viewModel: ContactListViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inflate the layout using DataBinding
        binding = DataBindingUtil.setContentView(this, R.layout.activity_contact_list)

        // Set up click listener for back button
        binding.btnArrowBack.setOnClickListener {
            // Navigate back to the main activity
            // Clear the activity stack and start the MainActivity
            val intent = Intent(this@ContactListActivity, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }

        // Set up click listener for exit button
        binding.btnExit.setOnClickListener {
            // Exit from the app
            this.finish()
        }

    }

}