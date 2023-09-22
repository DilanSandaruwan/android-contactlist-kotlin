package com.dilan.example.android.ctcontactlistapplication.views.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dilan.example.android.ctcontactlistapplication.databinding.ContactCardItemBinding
import com.dilan.example.android.ctcontactlistapplication.model.ContactData

/**
 * Adapter class for displaying the list of contact data in a RecyclerView using data binding.
 *
 * @param itemClickListener An interface to handle item click events.
 * @param deleteClickListener An interface to handle delete click events.
 */
class ContactListAdapter(
    private val itemClickListener: OnItemClickListener,
    private val deleteClickListener: OnDeleteClickListener,
) : ListAdapter<ContactData, ContactListAdapter.ViewHolder>(diff_util) {

    // Declare and initialize views within the item layout
    class ViewHolder(private val binding: ContactCardItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(
            item: ContactData,
            itemClickListener: OnItemClickListener,
            deleteClickListener: OnDeleteClickListener
        ) {
            binding.apply {
                // Bind data to the views using data binding
                contactName.text = when {
                    item.firstName.isNullOrEmpty() && !item.lastName.isNullOrEmpty() -> item.lastName.toString()
                    !item.firstName.isNullOrEmpty() && item.lastName.isNullOrEmpty() -> item.firstName.toString()
                    else -> "${item.firstName} ${item.lastName}"
                }

                // Set click listeners using data binding
                root.setOnClickListener { itemClickListener.itemClick(item) }
                btnDelete.setOnClickListener { deleteClickListener.deleteClick(item) }

                // Set the current item to the 'contact' variable
                contact = item

                // Execute pending bindings to update the UI
                executePendingBindings()

            }
        }

    }

    // Create the ViewHolder by inflating the item layout
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val binding = ContactCardItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    // Bind data to the ViewHolder views
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, itemClickListener, deleteClickListener)

    }

    // Interface to handle item click events
    interface OnItemClickListener {
        fun itemClick(item: ContactData)

    }

    // Interface to handle delete click events
    interface OnDeleteClickListener {
        fun deleteClick(item: ContactData)
    }

    // DiffUtil callback to optimize RecyclerView updates
    companion object {

        val diff_util = object : DiffUtil.ItemCallback<ContactData>() {

            override fun areItemsTheSame(oldItem: ContactData, newItem: ContactData): Boolean {
                return oldItem.phoneNumber == newItem.phoneNumber
            }

            override fun areContentsTheSame(
                oldItem: ContactData,
                newItem: ContactData
            ): Boolean {
                return oldItem == newItem
            }

        }
    }
}