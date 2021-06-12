package com.beanstage.clientinfo.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.beanstage.clientinfo.R
import com.beanstage.clientinfo.adapters.AddressListAdapter
import com.beanstage.clientinfo.app.ClientApplication
import com.beanstage.clientinfo.room.entities.Address
import com.beanstage.clientinfo.room.entities.Client
import com.beanstage.clientinfo.viewmodels.AddressViewModel
import com.beanstage.clientinfo.viewmodels.ClientViewModel

class ClientFormActivity : AppCompatActivity() {

    private lateinit var clientViewModel: ClientViewModel
    private lateinit var addressViewModel: AddressViewModel

    private lateinit var  addEditAddressButton: Button

    var currentClientName = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_client_form)

        clientViewModel = ClientViewModel((application as ClientApplication).clientRepository)
        addressViewModel = AddressViewModel((application as ClientApplication).addressRepository)

        addEditAddressButton = findViewById<Button>(R.id.add_edit_address_button)

        val recyclerView = findViewById<RecyclerView>(R.id.address_recyclerview)
        val adapter = AddressListAdapter()
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        addressViewModel.getAllByClientName("joel").observe(this) { addresses ->
            addresses.let {  adapter.submitList(addresses) }
        }

        val addEditClientButton = findViewById<Button>(R.id.add_edit_client_button)
        enabledAddressSaveButton()
        addEditClientButton.setOnClickListener {
            val newClient = getEditingClient()
                clientViewModel.insert(newClient)
                currentClientName = newClient.clientName
                enabledAddressSaveButton()
                Toast.makeText(this, "Guardado exitoso !!!", Toast.LENGTH_LONG).show()
        }


        addEditAddressButton.setOnClickListener {
            val newAddress = getEditingAddress()

            addressViewModel.insert(newAddress)

        }
    }

    fun getEditingClient() : Client {
        val clientName = findViewById<EditText>(R.id.client_name_editText)
        val clientSocialReason = findViewById<EditText>(R.id.social_reason_editText)
        val clientContactAgent = findViewById<EditText>(R.id.contact_agent_editText)

        val buisnessName = clientName.text.toString()
        val socialReason = clientSocialReason.text.toString()
        val contactAgent =  clientContactAgent.text.toString()


        return Client(buisnessName, socialReason, contactAgent)
    }

    fun getEditingAddress() : Address {
        val section = findViewById<EditText>(R.id.sector_editText)
        val street = findViewById<EditText>(R.id.street_editText)
        val number = findViewById<EditText>(R.id.number_editText)
        val reference = findViewById<EditText>(R.id.address_reference_editText)

        val sectionValue = section.text.toString()
        val streetValue = street.text.toString()
        val numberValue = number.text.toString()
        val referenceValue = reference.text.toString()

        section.text.clear()
        street.text.clear()
        number.text.clear()
        reference.text.clear()

        return Address(clientName = "joel", sectorName = sectionValue, streetName = streetValue,
            number = numberValue, reference = referenceValue)
    }

    fun enabledAddressSaveButton() {
        addEditAddressButton.isEnabled = currentClientName.isNotEmpty()
    }
}