package com.beanstage.clientinfo.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.beanstage.clientinfo.R
import com.beanstage.clientinfo.adapters.ContactListAdapter
import com.beanstage.clientinfo.app.ContactApplication
import com.beanstage.clientinfo.viewmodels.ContactViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton

class ContactActivity : AppCompatActivity() {


    private lateinit var viewModel: ContactViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contact)

        viewModel = ContactViewModel((application as ContactApplication).ContactRepository)

        val recyclerView = findViewById<RecyclerView>(R.id.contact_recyclerview)
        val adapter = ContactListAdapter(viewModel)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        viewModel.allContacts.observe(this) { contacts ->
            contacts.let {  adapter.submitList(contacts) }
        }

        val fab = findViewById<FloatingActionButton>(R.id.new_client_fab)
        fab.setOnClickListener {
            val intent = Intent(this, ContactFormActivity::class.java)
            startActivity(intent)
        }
    }
}