package com.beanstage.clientinfo.viewmodels

import androidx.lifecycle.*
import com.beanstage.clientinfo.repositories.AddressRepository
import com.beanstage.clientinfo.repositories.ClientRepository
import com.beanstage.clientinfo.room.entities.Address
import com.beanstage.clientinfo.room.entities.Client
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class AddressViewModel(private val repository: AddressRepository) : ViewModel(){

 fun getAllByClientName(clientId: Long): LiveData<List<Address>> {
       return  repository.getAllByClientName(clientId).asLiveData()
    }

    fun getAddressById(addressId: Long): Flow<Address> {
        return repository.getAddressById(addressId)
    }

    fun insert(address: Address) = viewModelScope.launch {
        repository.insert(address)
    }

    fun edit(address: Address) = viewModelScope.launch {
        repository.edit(address)
    }

    fun delete(address: Address) = viewModelScope.launch {
        repository.delete(address)
    }
}