package com.beanstage.clientinfo.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.beanstage.clientinfo.R
import com.beanstage.clientinfo.adapters.ClientListAdapter
import com.beanstage.clientinfo.app.ClientApplication
import com.beanstage.clientinfo.room.entities.Client
import com.beanstage.clientinfo.viewmodels.ClientViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton

class ClientActivity : AppCompatActivity() {


    private lateinit var viewModel: ClientViewModel

    private val newWordActivityRequestCode = 1
//    private val clientViewModel: ClientViewModel by viewModels {
//        ClientViewModelFactory((application as ClientApplication).repository)
//    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.client_activity)
//        viewModel = ViewModelProvider(this).get(ClientViewModel::class.java)

        viewModel = ClientViewModel((application as ClientApplication).repository)



        val recyclerView = findViewById<RecyclerView>(R.id.client_recyclerview)
        val adapter = ClientListAdapter()
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        viewModel.allClients.observe(this) { clients ->
            // Update the cached copy of the words in the adapter.
            clients.let {  adapter.submitList(clients) }
        }

        val fab = findViewById<FloatingActionButton>(R.id.new_client_fab)
        fab.setOnClickListener {
            val intent = Intent(this, ClientFormActivity::class.java)
            startActivity(intent)
        }
    }
}