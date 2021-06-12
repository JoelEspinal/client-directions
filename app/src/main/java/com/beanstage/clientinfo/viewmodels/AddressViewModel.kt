package com.beanstage.clientinfo.viewmodels

import androidx.lifecycle.*
import com.beanstage.clientinfo.repositories.AddressRepository
import com.beanstage.clientinfo.repositories.ClientRepository
import com.beanstage.clientinfo.room.entities.Address
import kotlinx.coroutines.launch

class AddressViewModel(private val repository: AddressRepository) : ViewModel(){

//        val allClients: LiveData<List<Client>> =

     fun getAllByClientName(clientName: String): LiveData<List<Address>> {
       return  repository.getAllByClientName(clientName).asLiveData()
    }

    fun insert(address: Address) = viewModelScope.launch {
        repository.insert(address)
    }
}