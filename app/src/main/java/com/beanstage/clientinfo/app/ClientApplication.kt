package com.beanstage.clientinfo.app

import android.app.Application
import com.beanstage.clientinfo.repositories.ClientRepository
import com.beanstage.clientinfo.room.ClientRoomDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class ClientApplication : Application() {

    private val applicationScope = CoroutineScope(SupervisorJob())

    val database by lazy { ClientRoomDatabase.getDatabase(this, applicationScope) }
    val repository by lazy { ClientRepository(database.clientDao()) }
}