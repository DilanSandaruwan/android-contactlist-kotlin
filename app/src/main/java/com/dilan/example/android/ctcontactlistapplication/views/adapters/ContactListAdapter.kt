package com.dilan.example.android.ctcontactlistapplication.views.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView.OnItemClickListener
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dilan.example.android.ctcontactlistapplication.R
import com.dilan.example.android.ctcontactlistapplication.model.ContactData
import com.google.android.material.textfield.TextInputEditText

class ContactListAdapter(
    private val itemClickListener: OnItemClickListener,
    private val deleteClickListener: OnDeleteClickListener,
) : ListAdapter<ContactData, ContactListAdapter.ViewHolder>(diff_util) {

    // Declare and initialize views within the item layout
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var txtContactName = itemView.findViewById<TextView>(R.id.contactName)
        var etFirstName: TextInputEditText = itemView.findViewById(R.id.et_contact_first_name)
        var etLastName: TextInputEditText =
            itemView.findViewById(R.id.et_contact_last_name)
        var etPhone1: TextInputEditText =
            itemView.findViewById(R.id.et_contact_number_1)
        var etEmail: TextInputEditText =
            itemView.findViewById(R.id.et_contact_email)
    }

    // Create the ViewHolder by inflating the item layout
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.contact_card_item, parent, false)
        return ViewHolder(view)
    }

    // Bind data to the ViewHolder views
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)

        // Display contact name based on available data
        holder.txtContactName.text =
            if (item.firstName.isNullOrEmpty()) {
                item.lastName.toString()
            } else if (item.lastName.isNullOrEmpty()) {
                item.firstName.toString()
            } else {
                "${item.firstName} ${item.lastName}"
            }

        // Set data to the EditText views
        holder.etFirstName.setText(item.firstName)
        holder.etLastName.setText(item.lastName)
        holder.etPhone1.setText(item.phoneNumber1)
        holder.etEmail.setText(item.email)

        // Set click listener for item click
        holder.itemView.setOnClickListener {
            itemClickListener.itemClick(item)
        }

        // Set click listener for delete button
        holder.itemView.findViewById<ImageView>(R.id.btn_delete).setOnClickListener {
            deleteClickListener.deleteClick(item)
        }
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
                return oldItem.phoneNumber1 == newItem.phoneNumber1
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