package com.dilan.example.android.ctcontactlistapplication.viewmodels

import android.util.Log
import androidx.databinding.Bindable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dilan.example.android.ctcontactlistapplication.model.ContactData
import com.dilan.example.android.ctcontactlistapplication.repository.ContactRepository
import kotlinx.coroutines.launch

/**
 * ViewModel class responsible for managing contact data and navigation state.
 */
class ContactListViewModel(private val contactRepo: ContactRepository) : ViewModel() {

    // Private MutableLiveData to hold the main list of contacts.
    private var _contactList = MutableLiveData<List<ContactData>>()
    val contactList: LiveData<List<ContactData>>
        get() = _contactList

    // Private MutableLiveData to hold the search results list of contacts.
    private var _contactSearchList = MutableLiveData<List<ContactData>>()
    val contactSearchList: LiveData<List<ContactData>>
        get() = _contactSearchList

    // Private MutableLiveData to hold the search string.
    private var _strToSearch = MutableLiveData<String?>()
    val strToSearch: LiveData<String?>
        get() = _strToSearch

    // Private MutableLiveData to trigger navigation to add new contact screen.
    private var _navigateToAddNewContact = MutableLiveData<Boolean>(false)
    val navigateToAddNewContact: LiveData<Boolean>
        get() = _navigateToAddNewContact

    // Private MutableLiveData to hold the details of the selected contact.
    private var _selectedContact = MutableLiveData<ContactData?>(null)

    /**
     * LiveData property providing the details of the selected contact.
     */
    @get:Bindable
    val selectedContact: LiveData<ContactData?>
        get() = _selectedContact

    // Private MutableLiveData to trigger to load contacts list freshly.
    private var _refreshContactList = MutableLiveData<Boolean>(false)
    val refreshContactList: LiveData<Boolean>
        get() = _refreshContactList

    /**
     * Set the main list of contacts.
     */
    fun setContactList(arrayList: List<ContactData>) {
        _contactList.value = arrayList
    }

    /**
     * Set the search results list of contacts.
     */
    fun setContactSearchList(arrayList: List<ContactData>) {
        _contactSearchList.value = arrayList
    }

    /**
     * Set the search string for filtering contacts.
     */
    fun setStrToSearch(str: String) {
        _strToSearch.value = str
    }

    /**
     * Set the details of the selected contact.
     */
    fun setSelectedContactDetails(selectedContact: ContactData?) {
        _selectedContact.value = selectedContact
    }

    /**
     * Trigger navigation to add new contact screen.
     */
    fun addNewContactData(navToDetails: Boolean) {
        _navigateToAddNewContact.value = navToDetails
    }

    /**
     * Saves the given [contactData] into the database using a background coroutine.
     *
     * @param contactData The contact data to be saved.
     */
    fun saveContactInDb(contactData: ContactData) {
        viewModelScope.launch {
            val response = contactRepo.insertContact(contactData)
            Log.e("INSERT", "saveContactInDb: $response")
            _refreshContactList.postValue(true)
        }
    }

    /**
     * Updates the given [contactData] in the database using a background coroutine.
     *
     * @param contactData The contact data to be updated.
     */
    fun updateContactInDb(contactData: ContactData) {
        viewModelScope.launch {
            val response = contactRepo.updateContact(contactData)
            Log.e("UPDATE", "updateContactInDb: $response")
            _refreshContactList.postValue(true)
        }
    }

    /**
     * Create and set an ArrayList of initial contact data.
     */
    fun initiateContactsList() {
        var contactList: List<ContactData> = mutableListOf()

        // Add sample contact data to the list
        viewModelScope.launch {
            contactList = contactRepo.getContacts()
            Log.e("CONTACTS_LIST", "initiateContactsList: $contactList")
            setContactList(contactList)
        }

    }

    init {
        initiateContactsList()
    }
}