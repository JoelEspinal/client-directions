package com.beanstage.clientinfo.repositories

import androidx.annotation.WorkerThread
import com.beanstage.clientinfo.room.daos.ContactDao
import com.beanstage.clientinfo.room.entities.Contact

class ContactRepository(private val userDao: ContactDao){


    val allContacts = userDao.getAll()

    fun getContactById(contactId: Long) = userDao.getContactById(contactId)

    @WorkerThread
    suspend fun insert(contact: Contact): Long {
        return userDao.insert(contact)
    }

    @WorkerThread
    suspend fun edit(contact: Contact) {
        userDao.edit(contact)
    }

}
