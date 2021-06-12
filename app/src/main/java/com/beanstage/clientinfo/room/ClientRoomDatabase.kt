package com.beanstage.clientinfo.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.beanstage.clientinfo.room.daos.AddressDao
import com.beanstage.clientinfo.room.daos.ClientDao
import com.beanstage.clientinfo.room.entities.Address
import com.beanstage.clientinfo.room.entities.Client
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@Database(entities = [Client::class, Address::class], version = 1)
abstract class ClientRoomDatabase : RoomDatabase() {

    abstract fun clientDao(): ClientDao
    abstract fun addressDao(): AddressDao

    companion object {
        @Volatile
        private var INSTANCE: ClientRoomDatabase? = null

        fun getDatabase(
            context: Context,
            scope: CoroutineScope
        ): ClientRoomDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ClientRoomDatabase::class.java,
                    "clients_database"
                )
                    // Wipes and rebuilds instead of migrating if no Migration object.
                    // Migration is not part of this codelab.
                    .fallbackToDestructiveMigration()
                    .addCallback(WordDatabaseCallback(scope))
                    .build()
                INSTANCE = instance
                // return instance
                instance
            }
        }

        private class WordDatabaseCallback(
            private val scope: CoroutineScope
        ) : RoomDatabase.Callback() {
            /**
             * Override the onCreate method to populate the database.
             */
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                // If you want to keep the data through app restarts,
                // comment out the following line.
                INSTANCE?.let { database ->
                    scope.launch(Dispatchers.IO) {
                        populateDatabase(database.clientDao())
                    }
                }
            }
        }

        /**
         * Populate the database in a new coroutine.
         * If you want to start with more words, just add them.
         */
        suspend fun populateDatabase(clientDao: ClientDao) {
            // Start the app with a clean database every time.
            // Not needed if you only populate on creation.
            clientDao.deleteAll()

            clientDao.insert(Client(clientName = "Aguas DG", socialReason = "distribucion de agua", contactAgent = "Adian De Dios"))
            clientDao.insert( Client(clientName = "Revsoft", socialReason = "consultora de tecnologia", contactAgent = "Samuel Luis"))
            clientDao.insert( Client(clientName = "Bean Stage", socialReason = "A+D laboratrio", contactAgent = "Joel Espinals"))
        }
    }
}
