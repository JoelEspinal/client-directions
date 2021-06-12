package com.beanstage.clientinfo.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.beanstage.clientinfo.R
import com.beanstage.clientinfo.adapters.ClientListAdapter
import com.beanstage.clientinfo.app.ClientApplication
import com.beanstage.clientinfo.viewmodels.ClientViewModel

class ClientFormActivity : AppCompatActivity() {

    private lateinit var viewModel: ClientViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_client_form)

        viewModel = ClientViewModel((application as ClientApplication).repository)



        val recyclerView = findViewById<RecyclerView>(R.id.address_recyclerview)
        val adapter = ClientListAdapter()
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        viewModel.allClients.observe(this) { clients ->
            // Update the cached copy of the words in the adapter.
            clients.let {  adapter.submitList(clients) }
        }
    }
}