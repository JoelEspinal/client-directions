package com.beanstage.clientinfo.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.beanstage.clientinfo.R
import com.beanstage.clientinfo.activities.ADDRESS_ID
import com.beanstage.clientinfo.activities.AddressActivity
import com.beanstage.clientinfo.room.entities.Contact

class ContactListAdapter : ListAdapter<Contact, ContactListAdapter.ContactViewHolder>(CONTACT_COMPARATOR)  {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        return ContactViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: ContactListAdapter.ContactViewHolder, position: Int) {
        val currentContact = getItem(position)
        holder.bind(currentContact)
    }

    class ContactViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val context = itemView.context
        private val constraintLayout: ConstraintLayout = itemView.findViewById<ConstraintLayout>(R.id.contact_item)
        private val nameTextView: TextView = itemView.findViewById(R.id.sector_editText)
        private val lastNameTextView: TextView = itemView.findViewById(R.id.street_textview)
        private val referenceTextView: TextView = itemView.findViewById(R.id.number_editText)

        fun bind(contact: Contact?) {
            nameTextView.text = contact?.name
            lastNameTextView.text = contact?.lastName
            referenceTextView.text = contact?.reference

            constraintLayout.setOnClickListener {
                val intent = Intent(context, AddressActivity::class.java)
                intent.putExtra(ADDRESS_ID, contact?.contactId)
                context.startActivity(intent)
            }
        }

        companion object {
            fun create(parent: ViewGroup): ContactViewHolder {
                val view: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.recycleview_contact_item, parent, false)
                return ContactViewHolder(view)
            }
        }
    }

    companion object {
        private val CONTACT_COMPARATOR = object : DiffUtil.ItemCallback<Contact>() {
            override fun areItemsTheSame(old: Contact, new: Contact): Boolean {
                return old == new
            }

            override fun areContentsTheSame(old: Contact, new: Contact): Boolean {
                return (old.contactId == new.contactId
                        && old.name == new.name
                        && old.lastName == new.lastName
                        && old.reference == new.reference)
            }
        }
    }
}