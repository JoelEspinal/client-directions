package com.beanstage.clientinfo.room.daos

import androidx.room.*
import com.beanstage.clientinfo.room.entities.Contact
import kotlinx.coroutines.flow.Flow

@Dao
interface ContactDao {

    @Query("SELECT * FROM contact_table WHERE contactId = :contactId")
    suspend fun getContactById(contactId: Long): Contact


    @Query("SELECT * FROM contact_table")
    fun getAll(): Flow<List<Contact>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(contact: Contact): Long

    @Query("DELETE FROM contact_table")
    suspend fun deleteAll()

    @Query("DELETE FROM contact_table where contactId = :contactId")
    suspend fun deleteContact(contactId: Long)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun edit(client: Contact)
}

