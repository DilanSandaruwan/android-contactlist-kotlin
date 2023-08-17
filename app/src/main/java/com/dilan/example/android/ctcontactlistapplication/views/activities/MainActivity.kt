package com.dilan.example.android.ctcontactlistapplication.views.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.dilan.example.android.ctcontactlistapplication.R
import com.dilan.example.android.ctcontactlistapplication.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        binding.btnContact.setOnClickListener {
            val intent = Intent(this@MainActivity, ContactListActivity::class.java)
            startActivity(intent)
        }
    }
}