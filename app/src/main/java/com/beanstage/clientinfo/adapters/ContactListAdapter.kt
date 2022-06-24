package com.beanstage.clientinfo.adapters

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.beanstage.clientinfo.R
import com.beanstage.clientinfo.activities.CONTACT_ID
import com.beanstage.clientinfo.activities.ContactFormActivity
import com.beanstage.clientinfo.room.entities.Contact
import com.beanstage.clientinfo.viewmodels.ContactViewModel

class ContactListAdapter(val contactViewModel: ContactViewModel): ListAdapter<Contact, ContactListAdapter.ContactViewHolder>(CONTACT_COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        return ContactViewHolder.create(parent, contactViewModel)
    }

    override fun onBindViewHolder(holder: ContactListAdapter.ContactViewHolder, position: Int) {
        val currentContact = getItem(position)
        holder.bind(currentContact, contactViewModel)
    }

    class ContactViewHolder(itemView: View, viewModel: ContactViewModel) : RecyclerView.ViewHolder(itemView) {
        val context = itemView.context

        private val constraintLayout: ConstraintLayout = itemView.findViewById(R.id.contact_item)
        private val nameTextView: TextView = itemView.findViewById(R.id.sector_editText)
        private val lastNameTextView: TextView = itemView.findViewById(R.id.street_textview)
        private val referenceTextView: TextView = itemView.findViewById(R.id.number_editText)
        private val deleteButton: Button = itemView.findViewById(R.id.delete_button)

        fun bind(contact: Contact?, contactViewModel: ContactViewModel) {

            nameTextView.text = contact?.name
            lastNameTextView.text = contact?.lastName
            referenceTextView.text = contact?.reference

            constraintLayout.setOnClickListener {
                val intent = Intent(context, ContactFormActivity::class.java)
                intent.putExtra(CONTACT_ID, contact?.contactId)
                context.startActivity(intent)
            }

            deleteButton.setOnClickListener {
                contact?.let { it -> setupDeleteDialog(context,contactViewModel, contact) }
            }
        }

       fun setupDeleteDialog(context: Context, contactViewModel: ContactViewModel, contact: Contact) {

            val builder = AlertDialog.Builder(context)
            builder.setMessage(R.string.delete_contact_message)
                .setPositiveButton(R.string.delete_confirmation_message,
                    DialogInterface.OnClickListener { dialog, id ->
                            contactViewModel.delete(contact)
                    })
                .setNegativeButton(R.string.cancel_deleting_message,
                    DialogInterface.OnClickListener { dialog, id ->
                        // User cancelled the dialog
                    })
            // Create the AlertDialog object and return it
            builder.create()

            builder.show()
        }
        companion object {
            fun create(parent: ViewGroup, contactViewModel: ContactViewModel): ContactViewHolder {
                val view: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.recycleview_contact_item, parent, false)
                return ContactViewHolder(view, contactViewModel)
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