package com.beanstage.clientinfo.repositories

import androidx.activity.viewModels
import androidx.annotation.WorkerThread
import com.beanstage.clientinfo.app.ClientApplication
import com.beanstage.clientinfo.room.daos.ClientDao
import com.beanstage.clientinfo.room.entities.Client
import com.beanstage.clientinfo.viewmodels.ClientViewModel
import com.beanstage.clientinfo.viewmodels.ClientViewModelFactory
import kotlinx.coroutines.flow.Flow

class ClientRepository(private val clientDao: ClientDao) {

    val allClients : Flow<List<Client>> = clientDao.getAll() // clientDao.getClients()

    @WorkerThread
    suspend fun insert(client: Client) {
        clientDao.insert(client)
    }
}