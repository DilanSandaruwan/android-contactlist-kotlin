package com.dilan.example.android.ctcontactlistapplication.views.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_contact_list, container, false)
        viewModel = ViewModelProvider(requireActivity())[ContactListViewModel::class.java]
        binding.lifecycleOwner = this
        binding.contactListVM = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val _layoutManager = LinearLayoutManager(requireContext())
        val _dividerItemDecoration =
            DividerItemDecoration(requireContext(), _layoutManager.orientation)
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

        binding.rvContactList.layoutManager = _layoutManager
        binding.rvContactList.addItemDecoration(_dividerItemDecoration)
        binding.rvContactList.adapter = adapter

        binding.fab.setOnClickListener {
            viewModel.addNewContactData(true)
        }

        viewModel.contactList.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }

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

    fun gotoDetailsFragment(item: ContactData?) {
        val action =
            ContactListFragmentDirections
                .actionContactListFragmentToDetailsFragment(item)
        view?.findNavController()?.navigate(action)
    }

    private fun deleteItem(item: ContactData) {
        val currentItems = viewModel.contactList.value?.toMutableList() ?: mutableListOf()
        currentItems.remove(item)
        viewModel.setContactList(currentItems)
        adapter.submitList(ArrayList(currentItems))
    }

    fun saveData(newContact: ContactData) {
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