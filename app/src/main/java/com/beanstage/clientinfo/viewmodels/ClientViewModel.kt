package com.beanstage.clientinfo.viewmodels

import androidx.lifecycle.*
import com.beanstage.clientinfo.repositories.ClientRepository
import com.beanstage.clientinfo.room.entities.Client
import kotlinx.coroutines.launch

class ClientViewModel(private val repository: ClientRepository) : ViewModel() {
    val allClients: LiveData<List<Client>> = repository.allClients.asLiveData()

//     val  allClients() : LiveData<List<Client>>{
//        var clients = repository.allClients
//
//         return clients.asLiveData()
//    }

    fun insert(client: Client) = viewModelScope.launch {
        repository.insert(client)
    }
}