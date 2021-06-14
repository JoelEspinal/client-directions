package com.beanstage.clientinfo.viewmodels

import androidx.lifecycle.*
import com.beanstage.clientinfo.repositories.ClientRepository
import com.beanstage.clientinfo.room.entities.Address
import com.beanstage.clientinfo.room.entities.Client
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class ClientViewModel(private val repository: ClientRepository) : ViewModel() {
    val allClients: LiveData<List<Client>> = repository.allClients.asLiveData()

     fun getCurrentClient(clientId: Long): Flow<Client> {
       return repository.getCurrentClient(clientId)
    }

    fun insert(client: Client): LiveData<Long?> {
        val insertedId = MutableLiveData<Long?>()

        viewModelScope.launch {
            val value =  repository.insert(client)
            insertedId.postValue(value)
        }
        return insertedId
    }

    fun edit(client: Client) = viewModelScope.launch {
        repository.edit(client)
    }
}