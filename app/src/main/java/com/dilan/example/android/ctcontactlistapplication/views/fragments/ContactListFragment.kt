package com.dilan.example.android.ctcontactlistapplication.views.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.ImageButton
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.dilan.example.android.ctcontactlistapplication.R
import com.dilan.example.android.ctcontactlistapplication.databinding.FragmentContactListBinding
import com.dilan.example.android.ctcontactlistapplication.model.ContactData
import com.dilan.example.android.ctcontactlistapplication.viewmodels.ContactListViewModel
import com.dilan.example.android.ctcontactlistapplication.viewmodels.ContactListViewModelProviderFactory
import com.dilan.example.android.ctcontactlistapplication.views.adapters.ContactListAdapter
import java.util.Locale

class ContactListFragment : Fragment() {

    // Declare lateinit variables
    lateinit var binding: FragmentContactListBinding
    lateinit var ctAdapter: ContactListAdapter
    val args: ContactListFragmentArgs by navArgs()

    /**---After Creating ViewModelFactory START---**/
    private val viewModel: ContactListViewModel by lazy {
        val viewModelProviderFactory = ContactListViewModelProviderFactory()
        ViewModelProvider(this, viewModelProviderFactory)[ContactListViewModel::class.java]
    }

    /**---After Creating ViewModelFactory FINISH---**/

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout using DataBinding
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_contact_list, container, false)

        // Bind the ViewModel to the layout
        binding.lifecycleOwner = this
        binding.contactListVM = viewModel

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Create layout manager and item decoration for RecyclerView
        val ctLayoutManager = LinearLayoutManager(requireContext())
        val dividerItemDecoration =
            DividerItemDecoration(requireContext(), ctLayoutManager.orientation)

        // Initialize the adapter with click listeners
        ctAdapter = ContactListAdapter(object :
            ContactListAdapter.OnItemClickListener {
            override fun itemClick(item: ContactData) {
                gotoDetailsFragment(item)
            }
        }, object : ContactListAdapter.OnDeleteClickListener {
            override fun deleteClick(item: ContactData) {
                deleteItem(item)
            }
        })

        // Configure RecyclerView with layout manager, item decoration, and adapter
        binding.rvContactList.apply {
            this.layoutManager = ctLayoutManager
            this.addItemDecoration(dividerItemDecoration)
            this.adapter = ctAdapter
        }

        // Set click listener for "Add New Contact" FAB
        binding.fab.setOnClickListener {
            viewModel.addNewContactData(true)
        }

        activity?.findViewById<ImageButton>(R.id.btn_arrow_back)?.visibility = VISIBLE

        // Observe LiveData for main contact list and update the adapter
        viewModel.contactList.observe(viewLifecycleOwner) {
            ctAdapter.submitList(it)
        }

        // Observe LiveData for filtered contact search list and update the adapter
        viewModel.contactSearchList.observe(viewLifecycleOwner) {
            ctAdapter.submitList(it)
        }

        // Set up search input listener
        binding.searchInputText
            .setOnEditorActionListener { editText, action, _ ->
                if (action == EditorInfo.IME_ACTION_SEARCH) {
                    editText.text.toString().let {
                        if (it.isBlank()) {
                            ctAdapter.submitList(viewModel.contactList.value)
                        }
                        viewModel.setStrToSearch(it)

                    }
                    return@setOnEditorActionListener true
                }
                return@setOnEditorActionListener false
            }

        // Observe LiveData for search input and filter the contact list accordingly
        viewModel.strToSearch.observe(viewLifecycleOwner) { searchQuery ->
            val normalizedQuery = searchQuery?.trim()?.lowercase(Locale.getDefault()) ?: ""
            val filteredContacts = viewModel.contactList.value?.filter { contactData ->
                (contactData.firstName?.contains(normalizedQuery, ignoreCase = true) == true) ||
                        (contactData.lastName?.contains(normalizedQuery, ignoreCase = true) == true)
            } ?: emptyList()

            viewModel.setContactSearchList(filteredContacts.ifEmpty { viewModel.contactList.value.orEmpty() })
        }

        // Observe LiveData for navigation to "Add New Contact" screen
        viewModel.navigateToAddNewContact.observe(viewLifecycleOwner, Observer<Boolean> {
            if (it) {
                gotoDetailsFragment(null)
            }
        })

    }

    override fun onStart() {
        super.onStart()
        if (args.isToSave) {
            args.objToSave?.let { saveData(it) }
        }
    }

    // Function to handle navigation to DetailsFragment
    fun gotoDetailsFragment(item: ContactData?) {
        val action =
            ContactListFragmentDirections
                .actionContactListFragmentToDetailsFragment(item)
        view?.findNavController()?.navigate(action)
    }

    // Function to delete a contact item
    private fun deleteItem(item: ContactData) {
        val currentItems = viewModel.contactList.value?.toMutableList() ?: mutableListOf()
        currentItems.remove(item)
        viewModel.setContactList(currentItems)
        ctAdapter.submitList(ArrayList(currentItems))
    }

    // Function to save contact data
    private fun saveData(newContact: ContactData) {
        val currentItems = viewModel.contactList.value?.toMutableList() ?: mutableListOf()

        // Check if a similar contact already exists
        if (!currentItems.any { it.phoneNumber == newContact.phoneNumber }) {
            // Add the new contact to the list
            currentItems.add(newContact)

            // Update the LiveData
            viewModel.setContactList(currentItems)

            // Update the adapter using DiffUtil
            ctAdapter.submitList(currentItems.toList())
        } else {
            // Check if a similar contact already exists and get the index of it in the list
            val index = currentItems.indexOfFirst { it.phoneNumber == newContact.phoneNumber }

            if (index != -1) {
                // Update the contact at the specified index
                currentItems[index] = newContact

                // Update the LiveData
                viewModel.setContactList(currentItems)

                // Update the adapter using DiffUtil
                ctAdapter.submitList(currentItems.toList())
            }
        }
    }

}