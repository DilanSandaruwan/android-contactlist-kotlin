package com.dilan.example.android.ctcontactlistapplication.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dilan.example.android.ctcontactlistapplication.ContactApp
import com.dilan.example.android.ctcontactlistapplication.repository.ContactRepositoryImpl

class ContactListViewModelProviderFactory() : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val contactDao = ContactApp.contactDatabase.contactDao()
        val contactRepository = ContactRepositoryImpl(contactDao = contactDao)

        val viewModel = ContactListViewModel(contactRepository)

        return viewModel as T
    }
}