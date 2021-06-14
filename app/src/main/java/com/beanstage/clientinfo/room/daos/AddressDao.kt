package com.beanstage.clientinfo.room.daos

import androidx.room.*
import com.beanstage.clientinfo.room.entities.Address
import com.beanstage.clientinfo.room.entities.Client
import kotlinx.coroutines.flow.Flow

@Dao
interface AddressDao {

        @Query("SELECT * FROM address_table where clientId=:clientId")
        fun getAllByClient(clientId: Long): Flow<List<Address>>

        @Query("SELECT * FROM address_table WHERE addressId = :addressId")
        fun getAddressById(addressId: Long): Flow<Address>

        @Insert(onConflict = OnConflictStrategy.REPLACE)
        suspend fun insert(address: Address)

        @Update(onConflict = OnConflictStrategy.REPLACE)
        suspend fun edit(address: Address)

        @Query("DELETE FROM address_table")
        suspend fun deleteAll()

        @Delete
        suspend fun delete(address: Address)
}