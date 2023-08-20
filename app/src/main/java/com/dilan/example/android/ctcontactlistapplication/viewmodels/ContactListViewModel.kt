package com.dilan.example.android.ctcontactlistapplication.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dilan.example.android.ctcontactlistapplication.model.ContactData

class ContactListViewModel() : ViewModel() {
    private var _contactList = MutableLiveData<List<ContactData>>()

    val contactList: LiveData<List<ContactData>>
        get() = _contactList

    fun setContactList(arrayList: List<ContactData>){
        _contactList.value = arrayList
    }
}