package com.dilan.example.android.ctcontactlistapplication.views.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
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
        val _dividerItemDecoration = DividerItemDecoration(requireContext(), _layoutManager.orientation)
        adapter = ContactListAdapter(object :
            ContactListAdapter.OnItemClickListener {
            override fun itemClick(item: ContactData) {
                /***
                 * TODO : click event for a recycler item
                 */
            }
        })

        binding.rvContactList.layoutManager = _layoutManager
        binding.rvContactList.addItemDecoration(_dividerItemDecoration)
        binding.rvContactList.adapter = adapter

        viewModel.contactList.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }
    }
}