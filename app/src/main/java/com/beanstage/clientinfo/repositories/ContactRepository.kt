package com.beanstage.clientinfo.repositories

import androidx.annotation.WorkerThread
import com.beanstage.clientinfo.room.daos.ContactDao
import com.beanstage.clientinfo.room.entities.Contact

class ContactRepository(private val contactDao: ContactDao){


    val allContacts = contactDao.getAll()

    @WorkerThread
    suspend fun getContactById(contactId: Long): Contact {
        return contactDao.getContactById(contactId)
    }

    @WorkerThread
    suspend fun insert(contact: Contact): Long {
        return contactDao.insert(contact)
    }

    @WorkerThread
    suspend fun edit(contact: Contact) {
        contactDao.edit(contact)
    }

    @WorkerThread
    suspend fun delete(contact: Contact) {
        contact.contactId?.let { contactDao.deleteContact(it) }
    }
}
