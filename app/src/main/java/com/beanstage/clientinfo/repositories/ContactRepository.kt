package com.beanstage.clientinfo.repositories

import androidx.annotation.WorkerThread
import com.beanstage.clientinfo.room.daos.ContactDao
import com.beanstage.clientinfo.room.entities.Contact

class ContactRepository(private val contactDao: ContactDao){


    val allContacts = contactDao.getAll()

    suspend fun getContactById(contactId: Long) = contactDao.getContactById(contactId)

    @WorkerThread
    suspend fun insert(contact: Contact): Long {
        return contactDao.insert(contact)
    }

    @WorkerThread
    suspend fun edit(contact: Contact) {
        contactDao.edit(contact)
    }

}
