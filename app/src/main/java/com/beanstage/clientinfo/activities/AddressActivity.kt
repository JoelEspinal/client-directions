package com.beanstage.clientinfo.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import androidx.lifecycle.lifecycleScope
import com.beanstage.clientinfo.R
import com.beanstage.clientinfo.app.ClientApplication
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_address)
        addressViewModel = AddressViewModel((application as ClientApplication).addressRepository)

        sectionEditText = findViewById<EditText>(R.id.sector_editText)
        streetEditText = findViewById<EditText>(R.id.street_editText)
        numberEditText = findViewById<EditText>(R.id.number_editText)
        referenceEditText = findViewById<EditText>(R.id.address_reference_editText)


        val addressId =  intent.getLongExtra(ADDRESS_ID, 0)
        lifecycleScope.launch {
            addressViewModel.getAddressById(addressId).collect {
                sectionEditText.setText(it.sectorName)
                streetEditText.setText(it.streetName)
                numberEditText.setText(it.number)
                referenceEditText.setText(it.reference)
            }
        }

    }
}