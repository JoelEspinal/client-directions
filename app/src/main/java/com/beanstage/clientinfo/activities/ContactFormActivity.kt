package com.beanstage.clientinfo.activities

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.beanstage.clientinfo.R
import com.beanstage.clientinfo.adapters.AddressListAdapter
import com.beanstage.clientinfo.viewmodels.AddressViewModel
import com.beanstage.clientinfo.app.ContactApplication
import com.beanstage.clientinfo.room.entities.Contact
import com.beanstage.clientinfo.viewmodels.ContactViewModel
import kotlinx.coroutines.launch

const val CONTACT_ID = "CLIENT_ID"

class ContactFormActivity : AppCompatActivity() {

    private lateinit var contactViewModel: ContactViewModel
    private lateinit var addressViewModel: AddressViewModel

    private lateinit var  addEditAddressButton: Button
    private lateinit var  addEditClientButton: Button

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: AddressListAdapter

    private lateinit var clientName: EditText
    private lateinit var clientSocialReason: EditText
    private lateinit var clientContactAgent: EditText

    var clientId : Long? = 0

    var  currentContact: Contact? = null

    private lateinit var contactName: EditText
    private lateinit var contactLastName: EditText
    private lateinit var contactReference: EditText

    private lateinit var saveButton: Button
    private lateinit var cancelButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contact_form)

        supportActionBar?.setDisplayHomeAsUpEnabled(true);

        contactViewModel = ContactViewModel((application as ContactApplication).ContactRepository)

        contactName = findViewById(R.id.name_editText)
        contactLastName = findViewById(R.id.last_name_editText)
        contactReference = findViewById(R.id.reference_editText)

        saveButton = findViewById(R.id.edit_button)
        cancelButton = findViewById(R.id.cancel_button)

        val incomingContact =  intent.getLongExtra(CONTACT_ID, 0)
        fillWithContact(incomingContact)

        saveButton.setOnClickListener {
            saveContact()
        }

        cancelButton.setOnClickListener{
            setupDeleteDialog()
        }
    }

    fun fillWithContact(contactId: Long) {
        if (contactId > 0) {
            lifecycleScope.launch {
                currentContact = contactViewModel.getContactById(contactId).value
                fillForm()
            }
        } else {
            actionBar?.title ?: "Nuevo Contacto"
        }
    }


    private fun fillForm() {
        currentContact.let {
            contactName.setText(it?.name)
            contactLastName.setText(it?.lastName)
            contactReference.setText(it?.reference)
        }
    }

    private fun saveContact() {
        val name = findViewById<EditText>(R.id.name_editText)
        val lastName = findViewById<EditText>(R.id.last_name_editText)
        val reference = findViewById<EditText>(R.id.reference_editText)

        val nameValue = name.text.toString()
        val lastNameValue = lastName.text.toString()
        val referenceValue = reference.text.toString()

        val newContact = Contact(null, nameValue, lastNameValue, referenceValue)

        if (currentContact != null) {
            currentContact.let {
                newContact.contactId = currentContact?.contactId
                contactViewModel.edit(newContact)
            }

            Toast.makeText(baseContext, "Guardado exitoso !!", Toast.LENGTH_LONG).show()
        } else {
            contactViewModel.insert(newContact).value
            Toast.makeText(baseContext, "Guardado exitoso !!", Toast.LENGTH_LONG).show()
        }
    }


    fun setupDeleteDialog() {
        val builder = AlertDialog.Builder(ContactFormActivity@this)
        builder.setMessage("Cancelar y salir?")
            .setPositiveButton("Si, salir",
                DialogInterface.OnClickListener { dialog, id ->
                   finish()
                })
            .setNegativeButton(R.string.cancel_deleting_message,
                DialogInterface.OnClickListener { dialog, id ->
                    // User cancelled the dialog
                })

        builder.create()
        builder.show()
    }
}