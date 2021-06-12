package com.beanstage.clientinfo.room.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.beanstage.clientinfo.room.entities.Address
import com.beanstage.clientinfo.room.entities.Client
import kotlinx.coroutines.flow.Flow

@Dao
interface AddressDao {

        @Query("SELECT * FROM address_table where clientName=:client_name")
        fun getAllByClient(client_name: String): Flow<List<Address>>

        @Insert(onConflict = OnConflictStrategy.REPLACE)
        suspend fun insert(address: Address)

        @Query("DELETE FROM address_table")
        suspend fun deleteAll()

}