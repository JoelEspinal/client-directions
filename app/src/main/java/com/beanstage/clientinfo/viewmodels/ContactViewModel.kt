package com.beanstage.clientinfo.viewmodels

import androidx.lifecycle.*
import com.beanstage.clientinfo.repositories.ContactRepository
import com.beanstage.clientinfo.room.entities.Contact
import kotlinx.coroutines.launch

class ContactViewModel(private val repository: ContactRepository) : ViewModel() {
    val allContacts: LiveData<List<Contact>> = repository.allContacts.asLiveData()

    suspend fun getContactById(contactId: Long): MutableLiveData<Contact?> {
        var requestedContact = MutableLiveData<Contact?>()
        val contact = repository.getContactById(contactId)
        requestedContact.value = contact
        return requestedContact
    }

    fun insert(contact: Contact): LiveData<Long?> {
        val insertedId = MutableLiveData<Long?>()

        viewModelScope.launch {
            val value = repository.insert(contact)
            insertedId.postValue(value)
        }
        return insertedId
    }

    fun edit(contact: Contact) {
        viewModelScope.launch {
            repository.edit(contact)
        }
    }

     fun delete(contact: Contact) {
         viewModelScope.launch {
             repository.delete(contact)
         }
    }
}