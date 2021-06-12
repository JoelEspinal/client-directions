package com.beanstage.clientinfo.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.beanstage.clientinfo.R
import com.beanstage.clientinfo.adapters.ClientListAdapter
import com.beanstage.clientinfo.app.ClientApplication
import com.beanstage.clientinfo.room.entities.Client
import com.beanstage.clientinfo.viewmodels.ClientViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton

class ClientFormActivity : AppCompatActivity() {

    private lateinit var clientViewModel: ClientViewModel
    var currentClientName = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_client_form)

        clientViewModel = ClientViewModel((application as ClientApplication).repository)

        val recyclerView = findViewById<RecyclerView>(R.id.address_recyclerview)
        val adapter = ClientListAdapter()
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        clientViewModel.allClients.observe(this) { clients ->
            // Update the cached copy of the words in the adapter.
            clients.let {  adapter.submitList(clients) }
        }

        val addEditClientButton = findViewById<Button>(R.id.add_edit_client_button)
        addEditClientButton.setOnClickListener {
            val newClient = getEditingClient()
            if (newClient.clientName !=  currentClientName) {
                clientViewModel.insert(newClient)
                currentClientName = newClient.clientName
            } else {
                Toast.makeText(this, "No puede guardar repetidos", Toast.LENGTH_LONG).show()
            }
        }
    }

    fun getEditingClient() : Client {
        val clientName = findViewById<EditText>(R.id.client_name_editText)
        val clientSocialReason = findViewById<EditText>(R.id.social_reason_editText)
        val clientContactAgent = findViewById<EditText>(R.id.contact_agent_editText)

        val buisnessName = clientName.text.toString()
        val socialReason = clientSocialReason.text.toString()
        val contactAgent =  clientContactAgent.text.toString()

        clientName.text.clear()
        clientSocialReason.text.clear()
        clientContactAgent.text.clear()

        return Client(buisnessName, socialReason, contactAgent)
    }
}