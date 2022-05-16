package com.beanstage.clientinfo.app

import android.app.Application
import com.beanstage.clientinfo.repositories.AddressRepository
import com.beanstage.clientinfo.repositories.ClientRepository
import com.beanstage.clientinfo.repositories.ContactRepository
import com.beanstage.clientinfo.room.ClientRoomDatabase
import com.beanstage.clientinfo.room.ContactRoomDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class ContactApplication : Application() {

    private val applicationScope = CoroutineScope(SupervisorJob())

    val database by lazy { ContactRoomDatabase.getDatabase(this, applicationScope) }
    val ContactRepository by lazy { ContactRepository(database.contactDao()) }
}