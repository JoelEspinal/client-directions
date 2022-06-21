package com.beanstage.clientinfo.room.daos

import androidx.room.*
import com.beanstage.clientinfo.room.entities.Client
import com.beanstage.clientinfo.room.entities.Contact
import kotlinx.coroutines.flow.Flow

@Dao
interface ContactDao {

    @Query("SELECT * FROM contact_table WHERE contactId = :contactId")
    fun getContactById(contactId: Long): Flow<Contact>

    @Query("SELECT * FROM contact_table")
    fun getAll(): Flow<List<Contact>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(contact: Contact): Long

    @Query("DELETE FROM contact_table")
    suspend fun deleteAll()

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun edit(client: Contact)
}

