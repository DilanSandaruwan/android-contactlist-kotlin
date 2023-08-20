package com.dilan.example.android.ctcontactlistapplication.views.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView.OnItemClickListener
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dilan.example.android.ctcontactlistapplication.R
import com.dilan.example.android.ctcontactlistapplication.model.ContactData
import com.google.android.material.textview.MaterialTextView

class ContactListAdapter(
    private val itemClickListener: OnItemClickListener
) : ListAdapter<ContactData, ContactListAdapter.ViewHolder>(diff_util) {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var txtContactName = itemView.findViewById<TextView>(R.id.contactName)
        var etFirstName = itemView.findViewById<MaterialTextView>(R.id.et_contact_first_name)
        var etLastName = itemView.findViewById<MaterialTextView>(R.id.et_contact_last_name)
        var etPhone1 = itemView.findViewById<MaterialTextView>(R.id.et_contact_number_1)
        var etEmail = itemView.findViewById<MaterialTextView>(R.id.et_contact_email)
    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.contact_card_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.txtContactName.text = item.firstName
        holder.etFirstName.text = item.firstName
        holder.etLastName.text = item.lastName
        holder.etPhone1.text = item.phoneNumber1
        holder.etEmail.text = item.email

        holder.itemView.setOnClickListener {
            itemClickListener.itemClick(item)
        }
    }

    interface OnItemClickListener {

        fun itemClick(item: ContactData)
    }

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