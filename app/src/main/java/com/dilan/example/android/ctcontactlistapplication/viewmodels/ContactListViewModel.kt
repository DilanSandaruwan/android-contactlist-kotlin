package com.dilan.example.android.ctcontactlistapplication.viewmodels

import androidx.databinding.Bindable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dilan.example.android.ctcontactlistapplication.model.ContactData

class ContactListViewModel() : ViewModel() {

    private var _contactList = MutableLiveData<List<ContactData>>()
    val contactList: LiveData<List<ContactData>>
        get() = _contactList

    private var _navigateToAddNewContact = MutableLiveData<Boolean>(false)
    val navigateToAddNewContact: LiveData<Boolean>
        get() = _navigateToAddNewContact


    private var _selectedContact = MutableLiveData<ContactData?>(null)

    @get:Bindable
    val selectedContact: LiveData<ContactData?>
        get() = _selectedContact

    fun setContactList(arrayList: List<ContactData>) {
        _contactList.value = arrayList
    }

    fun setSelectedContactDetails(selectedContact: ContactData?) {
        _selectedContact.value = selectedContact
    }

    fun addNewContactData(navToDetails: Boolean) {
        _navigateToAddNewContact.value = navToDetails
    }
}