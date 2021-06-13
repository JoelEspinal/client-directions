package com.beanstage.clientinfo.room.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.beanstage.clientinfo.room.entities.Client
import kotlinx.coroutines.flow.Flow

@Dao
interface ClientDao {
    @Query("SELECT * FROM client_table ORDER BY client_name ASC")
    fun getFromClient(): Flow<List<Client>>

    @Query("SELECT * FROM client_table WHERE clientId = :clientId")
    fun getCurrentClient(clientId: Long): Flow<Client>

    @Query("SELECT * FROM client_table")
    fun getAll(): Flow<List<Client>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(client: Client): Long

    @Query("DELETE FROM client_table")
    suspend fun deleteAll()
}