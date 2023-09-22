package com.dilan.example.android.ctcontactlistapplication.views.fragments

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.dilan.example.android.ctcontactlistapplication.R
import com.dilan.example.android.ctcontactlistapplication.databinding.FragmentDetailsBinding
import com.dilan.example.android.ctcontactlistapplication.model.ContactData
import com.dilan.example.android.ctcontactlistapplication.viewmodels.ContactListViewModel
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText

class DetailsFragment : Fragment() {

    // Declare lateinit variables
    lateinit var binding: FragmentDetailsBinding
    lateinit var viewModel: ContactListViewModel

    // Get arguments using SafeArgs
    private val args: DetailsFragmentArgs by navArgs()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    // Function called when the fragment is created
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Initialize ViewModel using ViewModelProvider
        viewModel = ViewModelProvider(requireActivity())[ContactListViewModel::class.java]

        // Inflate the layout using DataBinding
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_details, container, false)

        // Bind the ViewModel to the layout
        binding.lifecycleOwner = viewLifecycleOwner
        binding.detailVM = viewModel

        // Set up edit button click listener
        binding.btnEdit.setOnClickListener {
            with(binding) {
                etContactFirstName.isEnabled = true
                etContactLastName.isEnabled = true
                etContactNumber.isEnabled = args.contactData == null
                etContactEmail.isEnabled = true
            }
        }

        // Set up close button click listener
        binding.btnClose.setOnClickListener {
            gotoContactsListFragment(false, null)
        }

        // Set up save button click listener
        binding.btnSave.setOnClickListener {
            var contactData = viewModel.selectedContact.value

            contactData = contactData ?: ContactData(
                    binding.etContactFirstName.text.toString(),
                    binding.etContactLastName.text.toString(),
                    binding.etContactNumber.text.toString(),
                    binding.etContactEmail.text.toString(),
                )

            if (checkSavingAvailability() && validateEmail(binding.etContactEmail) && validateMobile(
                    binding.etContactNumber
                )
            ) {
                gotoContactsListFragment(true, contactData)
            } else {
                Snackbar.make(
                    binding.lytDetailCordinator,
                    getString(R.string.contact_validation_msg),
                    Snackbar.LENGTH_SHORT
                ).show()
            }
        }
        return binding.root
    }

    // Function called when the fragment's view is created
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // If args contain contactData, set it in ViewModel
        val contactData = args.contactData
        if (contactData != null) {
            viewModel.setSelectedContactDetails(contactData)
        } else {
            viewModel.setSelectedContactDetails(null)
        }

    }

    // Function to navigate to the ContactsListFragment
    private fun gotoContactsListFragment(isToSave: Boolean, objToSave: ContactData?) {
        viewModel.addNewContactData(false)
        val action =
            DetailsFragmentDirections
                .actionDetailsFragmentToContactListFragment(isToSave, objToSave)
        view?.findNavController()?.navigate(action)
    }

    // Function to check if saving is possible based on entered data
    private fun checkSavingAvailability(): Boolean {
        return if (!TextUtils.isEmpty(binding.etContactNumber.text.toString())) {
            !(TextUtils.isEmpty(binding.etContactFirstName.text.toString())) || !(TextUtils.isEmpty(
                binding.etContactLastName.text.toString()
            ))
        } else {
            false
        }

    }

    // Function to validate email input
    private fun validateEmail(view: TextInputEditText): Boolean {
        val email = view.text.toString().trim()
        return if (!TextUtils.isEmpty(email)) {
            if (email.matches(Regex("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"))) {
                view.error = null
                view.isEnabled = false
                true
            } else {
                view.error = getString(R.string.error_email)
                view.isEnabled = true
                false
            }
        } else {
            view.error = null
            view.isEnabled = true
            true
        }

    }

    // Function to validate mobile input
    private fun validateMobile(view: TextInputEditText): Boolean {
        val str = view.text.toString().trim()
        return if (str.startsWith("0")) {
            view.error = null
            view.isEnabled = false
            true
        } else {
            view.error = getString(R.string.error_contact)
            view.isEnabled = true
            false
        }
    }

}