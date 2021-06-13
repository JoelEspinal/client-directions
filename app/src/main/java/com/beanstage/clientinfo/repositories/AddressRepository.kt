package com.beanstage.clientinfo.repositories

import androidx.annotation.WorkerThread
import com.beanstage.clientinfo.room.daos.AddressDao
import com.beanstage.clientinfo.room.entities.Address
import com.beanstage.clientinfo.room.entities.Client
import kotlinx.coroutines.flow.Flow

class AddressRepository(private val addressDao: AddressDao) {

    @WorkerThread
    suspend fun insert(address: Address) {
        addressDao.insert(address)
    }

    @WorkerThread
    suspend fun edit(address: Address) {
        addressDao.edit(address)
    }

    fun getAddressById(addressId: Long): Flow<Address> {
        return  addressDao.getAddressById(addressId)
    }

    fun getAllByClientName(clientName: String) : Flow<List<Address>>{
      return  addressDao.getAllByClient(clientName)
    }
}