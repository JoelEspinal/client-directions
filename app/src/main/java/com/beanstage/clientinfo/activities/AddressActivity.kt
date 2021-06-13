package com.beanstage.clientinfo.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.beanstage.clientinfo.R
import com.beanstage.clientinfo.app.ClientApplication
import com.beanstage.clientinfo.room.entities.Address
import com.beanstage.clientinfo.viewmodels.AddressViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

const val ADDRESS_ID = "ADDRESS_ID"
class AddressActivity : AppCompatActivity() {

    private lateinit var addressViewModel: AddressViewModel

    private lateinit var sectionEditText: EditText
    private lateinit var streetEditText: EditText
    private lateinit var numberEditText: EditText
    private lateinit var referenceEditText: EditText
    private lateinit var editButton: Button

    private lateinit var address: Address


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_address)
        addressViewModel = AddressViewModel((application as ClientApplication).addressRepository)

        sectionEditText = findViewById(R.id.sector_editText)
        streetEditText = findViewById(R.id.street_editText)
        numberEditText = findViewById(R.id.number_editText)
        referenceEditText = findViewById(R.id.address_reference_editText)
        editButton = findViewById(R.id.edit_button)

        val addressId =  intent.getLongExtra(ADDRESS_ID, 0)

        lifecycleScope.launch {
            addressViewModel.getAddressById(addressId).collect {
                address = it
                sectionEditText.setText(it.sectorName)
                streetEditText.setText(it.streetName)
                numberEditText.setText(it.number)
                referenceEditText.setText(it.reference)
            }
        }

        editButton.setOnClickListener {
            address.sectorName =  sectionEditText.text.toString()
            address.streetName =  streetEditText.text.toString()
            address.number =  numberEditText.text.toString()
            address.reference =  referenceEditText.text.toString()
            if (address.sectorName.isNotEmpty() && address.streetName.isNotEmpty()
                && address.number.isNotEmpty() && address.reference.isNotEmpty()) {
                addressViewModel.edit(address)
                Toast.makeText(this, "Editado Correctamente !!", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this, ":( Debe completar todos los campos", Toast.LENGTH_LONG).show()
            }
        }
    }
}