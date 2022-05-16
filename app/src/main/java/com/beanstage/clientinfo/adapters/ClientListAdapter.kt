package com.beanstage.clientinfo.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.beanstage.clientinfo.R
import com.beanstage.clientinfo.activities.CLIENT_ID
import com.beanstage.clientinfo.activities.ClientFormActivity
import com.beanstage.clientinfo.room.entities.Client

class ClientListAdapter : ListAdapter<Client, ClientListAdapter.ClientViewHolder>(CLIENT_COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClientViewHolder {
        return ClientViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: ClientViewHolder, position: Int) {
        val currentClient = getItem(position)
        holder.bind(currentClient)
    }

    class ClientViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val context = itemView.context
        private val constraintLayout: ConstraintLayout = itemView.findViewById<ConstraintLayout>(R.id.client_item)
        private val clientNameTextView: TextView = itemView.findViewById(R.id.sector_editText)
        private val socialReason: TextView = itemView.findViewById(R.id.street_editText)
        private val agentNameTextView: TextView = itemView.findViewById(R.id.number_editText)

        fun bind(client: Client?) {
            clientNameTextView.text = client?.clientName
            socialReason.text = client?.socialReason
            agentNameTextView.text = client?.contactAgent

            constraintLayout.setOnClickListener {
                val intent = Intent(context, ClientFormActivity::class.java)
                intent.putExtra(CLIENT_ID, client?.clientId)
                context.startActivity(intent)
            }
        }

        companion object {
            fun create(parent: ViewGroup): ClientViewHolder {
                val view: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.recycleview_client_item, parent, false)
                return ClientViewHolder(view)
            }
        }
    }

    companion object {
        private val CLIENT_COMPARATOR = object : DiffUtil.ItemCallback<Client>() {
            override fun areItemsTheSame(oldClient: Client, newClient: Client): Boolean {
                return oldClient == newClient
            }

            override fun areContentsTheSame(oldClient: Client, newClient: Client): Boolean {
                return (oldClient.clientName == newClient.clientName
                        && oldClient.socialReason == newClient.socialReason
                        && oldClient.contactAgent == newClient.contactAgent)
            }
        }
    }
}