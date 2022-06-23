package com.beanstage.clientinfo.activities

import android.content.Context
import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.beanstage.clientinfo.R
import com.beanstage.clientinfo.adapters.AddressListAdapter
import com.beanstage.clientinfo.room.entities.Address
import com.beanstage.clientinfo.viewmodels.AddressViewModel
import com.beanstage.clientinfo.viewmodels.ClientViewModel
import com.beanstage.clientinfo.app.ContactApplication
import com.beanstage.clientinfo.room.entities.Client
import com.beanstage.clientinfo.room.entities.Contact
import com.beanstage.clientinfo.viewmodels.ContactViewModel
import kotlinx.coroutines.flow.first
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
//
//        addEditAddressButton = findViewById(R.id.edit_button)
//        addEditClientButton = findViewById(R.id.add_edit_client_button)
//
//        recyclerView = findViewById(R.id.address_recyclerview)
//
//        clientName = findViewById(R.id.client_name_editText)
//        clientSocialReason = findViewById(R.id.social_reason_editText)
//        clientContactAgent = findViewById(R.id.contact_agent_editText)



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
        // Create the AlertDialog object and return it
        builder.create()

        builder.show()
    }

//    override
//    fun onCreate2(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_contact_form)
////        clientViewModel = ClientViewModel((application as ContactApplication).ContactRepository)
////        addressViewModel = AddressViewModel((application as ClientApplication).addressRepository)
//
//        addEditAddressButton = findViewById(R.id.edit_button)
//        addEditClientButton = findViewById(R.id.add_edit_client_button)
//
//        recyclerView = findViewById(R.id.address_recyclerview)
//
//        clientName = findViewById(R.id.client_name_editText)
//        clientSocialReason = findViewById(R.id.social_reason_editText)
//        clientContactAgent = findViewById(R.id.contact_agent_editText)
//
//
//
//        val incomingClient =  intent.getLongExtra(CONTACT_ID, 0)
//        if (incomingClient > 0) {
//            clientId = incomingClient
//
//            lifecycleScope.launch {
//                clientViewModel.getCurrentClient(clientId!!).collect {
//                    clientName.setText(it.clientName)
//                    clientSocialReason.setText(it.socialReason)
//                    clientContactAgent.setText(it.contactAgent)
//                }
//            }
//        } else {
//            addEditAddressButton.isEnabled = false
//        }
//
//        adapter = AddressListAdapter()
//        recyclerView.adapter = adapter
//        recyclerView.layoutManager = LinearLayoutManager(this)
//
//        setupAddresses()
//
////        addEditClientButton.setOnClickListener {
////            val newClient = getEditingClient()
////            if (newClient.clientName.isNotEmpty() && newClient.contactAgent.isNotEmpty()
////                && newClient.socialReason.isNotEmpty()) {
////                    if (clientId!! > 0) {
////                       var editedClient = getEditingClient()
////                       editedClient.clientId = clientId
////
////                        clientViewModel.edit(editedClient)
////                    } else {
////                        val id = clientViewModel.insert(newClient)
////                        id.observe(this, Observer {
////                            clientId = it
////                            enabledAddressSaveButton(clientId!!)
////                            Toast.makeText(this, "Guardado exitoso !!!", Toast.LENGTH_LONG).show()
////                        })
////                    }
////            } else {
////                Toast.makeText(this, ":( debe completar todos los campos", Toast.LENGTH_LONG).show()
////            }
////        }
//
//        addEditAddressButton.setOnClickListener {
//            val newAddress = getEditingAddress()
//            if (newAddress.sectorName.isNotEmpty() && newAddress.streetName.isNotEmpty()
//                && newAddress.number.isNotEmpty() && newAddress.reference.isNotEmpty()) {
//                addressViewModel.insert(newAddress)
//                adapter.notifyDataSetChanged()
//                Toast.makeText(this, "Guardado exitoso !!!", Toast.LENGTH_LONG).show()
//            } else {
//                Toast.makeText(this, ":( Debe completar todos los campos", Toast.LENGTH_LONG).show()
//            }
//        }
//    }

//    fun setupAddresses() {
//        addressViewModel.getAllByClientName(clientId!!).observe(this) { addresses ->
//            addresses.let {  adapter.submitList(addresses) }
//        }
//    }

//    fun getEditingClient() : Client {
//        val buisnessName = clientName.text.toString()
//        val socialReason = clientSocialReason.text.toString()
//        val contactAgent =  clientContactAgent.text.toString()
//
//        return Client(null, buisnessName, socialReason, contactAgent)
//    }



//    fun getEditingAddress() : Address {
//        val section = findViewById<EditText>(R.id.name_editText)
//        val street = findViewById<EditText>(R.id.last_name_editText)
//        val number = findViewById<EditText>(R.id.reference_editText)
//        val reference = findViewById<EditText>(R.id.address_reference_editText)
//
//        val sectionValue = section.text.toString()
//        val streetValue = street.text.toString()
//        val numberValue = number.text.toString()
//        val referenceValue = reference.text.toString()
//
//        section.text.clear()
//        street.text.clear()
//        number.text.clear()
//        reference.text.clear()
//
//        return Address(null, clientId = clientId!!, sectorName = sectionValue, streetName = streetValue,
//            number = numberValue, reference = referenceValue)
//    }

//    fun enabledAddressSaveButton(currentClientId: Long) {
//        if (currentClientId > 0) {
//            addEditAddressButton.isEnabled = true
//            clientId = currentClientId
//            setupAddresses()
//        }
//    }

    // contacts code

}