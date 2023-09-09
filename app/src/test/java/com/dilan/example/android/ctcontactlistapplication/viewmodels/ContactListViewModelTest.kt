package com.dilan.example.android.ctcontactlistapplication.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.dilan.example.android.ctcontactlistapplication.model.ContactData
import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.MockitoAnnotations

internal class ContactListViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var contactListViewModel: ContactListViewModel

    @Before
    fun setUp() {
        contactListViewModel = ContactListViewModel()
    }

    @Test
    fun `setting contact list updates LiveData`() {
        // Arrange
        val arrList = listOf<ContactData>()

        // Act
        contactListViewModel.setContactList(arrList)

        // Assert
        val result = contactListViewModel.contactList.value
        assertEquals(arrList, result)
    }

    @Test
    fun `setting contact search list updates LiveData`() {
        // Arrange
        val arrList = listOf<ContactData>()

        // Act
        contactListViewModel.setContactSearchList(arrList)

        // Assert
        val result = contactListViewModel.contactSearchList.value
        assertEquals(arrList, result)
    }

    @Test
    fun `setting selected contact details updates LiveData`() {

        // Arrange
        val selectedContact = ContactData("Anjelo", "Mathews", "0771234567", "anjelomat@gmail.com")

        // Act
        contactListViewModel.setSelectedContactDetails(selectedContact)

        // Assert
        val result = contactListViewModel.selectedContact.value
        assertEquals(selectedContact, result)
    }

    @Test
    fun `setting search string updates LiveData`() {
        // Arrange
        val strEmpty = ""

        // Act
        contactListViewModel.setStrToSearch(strEmpty)

        // Assert
        val result = contactListViewModel.strToSearch.value
        assertEquals("", result)
    }

    @Test
    fun `adding new contact data updates LiveData`() {
        // Arrange
        val navToDetails = true

        // Act
        contactListViewModel.addNewContactData(navToDetails)

        // Assert
        val result = contactListViewModel.navigateToAddNewContact.value
        assertEquals(navToDetails, result)
    }
}