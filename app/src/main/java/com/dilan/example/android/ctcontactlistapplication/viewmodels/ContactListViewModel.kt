package com.dilan.example.android.ctcontactlistapplication.viewmodels

import androidx.databinding.Bindable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dilan.example.android.ctcontactlistapplication.model.ContactData

/**
 * ViewModel class responsible for managing contact data and navigation state.
 */
class ContactListViewModel() : ViewModel() {

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
     * Create and set an ArrayList of initial contact data.
     */
    private fun initiateContactsList() {
        var contactList: ArrayList<ContactData> = ArrayList()

        // Add sample contact data to the list
        contactList.apply {
            add(
                ContactData(
                    "Julee",
                    "Carnier",
                    "0762671438",
                    "jcarnier0@shutterfly.com"
                )
            )
            add(
                ContactData(
                    "Natalie",
                    "Graham",
                    "0762671423",
                    "ngrahm10@shutterfly.com"
                )
            )
        }

        // Set the initialized contact list
        setContactList(contactList)
    }

    init {
        initiateContactsList()
    }
}