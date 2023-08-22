package com.dilan.example.android.ctcontactlistapplication.views.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
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
import com.dilan.example.android.ctcontactlistapplication.views.adapters.ContactListAdapter

class ContactListFragment : Fragment() {

    // Declare lateinit variables
    lateinit var binding: FragmentContactListBinding
    lateinit var viewModel: ContactListViewModel
    lateinit var adapter: ContactListAdapter
    val args: ContactListFragmentArgs by navArgs()

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

        // Initialize ViewModel using ViewModelProvider
        viewModel = ViewModelProvider(requireActivity())[ContactListViewModel::class.java]

        // Bind the ViewModel to the layout
        binding.lifecycleOwner = this
        binding.contactListVM = viewModel

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Create layout manager and item decoration for RecyclerView
        val layoutManager = LinearLayoutManager(requireContext())
        val dividerItemDecoration =
            DividerItemDecoration(requireContext(), layoutManager.orientation)

        // Initialize the adapter with click listeners
        adapter = ContactListAdapter(object :
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
        binding.rvContactList.layoutManager = layoutManager
        binding.rvContactList.addItemDecoration(dividerItemDecoration)
        binding.rvContactList.adapter = adapter

        // Set click listener for "Add New Contact" FAB
        binding.fab.setOnClickListener {
            viewModel.addNewContactData(true)
        }

        // Observe LiveData for main contact list and update the adapter
        viewModel.contactList.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }

        // Observe LiveData for filtered contact search list and update the adapter
        viewModel.contactSearchList.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }

        // Set up search input listener
        binding.searchInputText
            .setOnEditorActionListener { editText, action, _ ->
                if (action == EditorInfo.IME_ACTION_SEARCH) {
                    editText.text.toString().let {
                        if (it.isBlank()) {
                            adapter.submitList(viewModel.contactList.value)
                        }
                        viewModel.setStrToSearch(it)

                    }
                    return@setOnEditorActionListener true
                }
                return@setOnEditorActionListener false
            }

        // Observe LiveData for search input and filter the contact list accordingly
        viewModel.strToSearch.observe(viewLifecycleOwner) {
            val strToSearch = it ?: ""
            val searchResults = ArrayList<ContactData>()

            val contactDataList =
                viewModel.contactList.value // Get the value from the MutableLiveData

            if (contactDataList != null) {
                if (strToSearch != "") {
                    for (contactData in contactDataList) {
                        // Assuming ContactData has a name field
                        if ((contactData.firstName?.contains(
                                strToSearch,
                                ignoreCase = true
                            ) == true) || (contactData.lastName?.contains(
                                strToSearch,
                                ignoreCase = true
                            ) == true)
                        ) {
                            searchResults.add(contactData)
                        }
                    }
                    viewModel.setContactSearchList(searchResults)
                } else {
                    adapter.submitList(viewModel.contactList.value)
                }

            }
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
        adapter.submitList(ArrayList(currentItems))
    }

    // Function to save contact data
    private fun saveData(newContact: ContactData) {
        val currentItems = viewModel.contactList.value?.toMutableList() ?: mutableListOf()

        // Check if a similar contact already exists
        if (!currentItems.any { it.phoneNumber1 == newContact.phoneNumber1 }) {
            // Add the new contact to the list
            currentItems.add(newContact)

            // Update the LiveData
            viewModel.setContactList(currentItems)

            // Update the adapter using DiffUtil
            adapter.submitList(currentItems.toList())
        } else {
            // Check if a similar contact already exists and get the index of it in the list
            val index = currentItems.indexOfFirst { it.phoneNumber1 == newContact.phoneNumber1 }

            if (index != -1) {
                // Update the contact at the specified index
                currentItems[index] = newContact

                // Update the LiveData
                viewModel.setContactList(currentItems)

                // Update the adapter using DiffUtil
                adapter.submitList(currentItems.toList())
            }
        }
    }

}