package com.beanstage.clientinfo.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.beanstage.clientinfo.R
import com.beanstage.clientinfo.room.entities.Address

class AddressListAdapter : ListAdapter<Address, AddressListAdapter.AddressViewHolder>(ADDRESS_COMPARATOR)  {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddressListAdapter.AddressViewHolder {
        return AddressListAdapter.AddressViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: AddressListAdapter.AddressViewHolder, position: Int) {
        val currentAddress = getItem(position)
        holder.bind(currentAddress)
    }

    class AddressViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val sectorTextView: TextView = itemView.findViewById(R.id.sector_textview)
        private val streetTextView: TextView = itemView.findViewById(R.id.street_textview)
        private val numberTextView: TextView = itemView.findViewById(R.id.number_textview)
        private val referenceTextView: TextView = itemView.findViewById(R.id.referernce_textview)

        fun bind(address: Address?) {
            sectorTextView.text = address?.sectorName
            streetTextView.text = address?.streetName
            numberTextView.text = address?.number
            referenceTextView.text = address?.reference
        }

        companion object {
            fun create(parent: ViewGroup): AddressViewHolder {
                val view: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.recycleview_address_item, parent, false)
                return AddressViewHolder(view)
            }
        }
    }

    companion object {
        private val ADDRESS_COMPARATOR = object : DiffUtil.ItemCallback<Address>() {
            override fun areItemsTheSame(old: Address, new: Address): Boolean {
                return old == new
            }

            override fun areContentsTheSame(old: Address, new: Address): Boolean {
                return (old.addressId == new.addressId
                        && old.clientName == new.clientName
                        && old.sectorName == new.sectorName
                        && old.streetName == new.streetName
                        && old.number == new.number
                        && old.reference == new.reference)
            }
        }
    }
}