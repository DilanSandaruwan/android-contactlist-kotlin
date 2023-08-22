package com.dilan.example.android.ctcontactlistapplication.views.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.dilan.example.android.ctcontactlistapplication.R
import com.dilan.example.android.ctcontactlistapplication.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    // Declare a lateinit variable for the DataBinding
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inflate the layout using DataBinding
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        // Set up click listener for the "Contact" button
        binding.btnContact.setOnClickListener {
            // Create an intent to navigate to the ContactListActivity
            val intent = Intent(this@MainActivity, ContactListActivity::class.java)

            // Start the ContactListActivity
            startActivity(intent)
        }
    }
}