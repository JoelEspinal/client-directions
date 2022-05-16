package com.beanstage.clientinfo.room.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "contact_table")
data class Contact (
    @PrimaryKey(autoGenerate = true) var contactId: Long? = null,
    @ColumnInfo(name = "contact_name") val name: String,
    @ColumnInfo(name = "contact_last_name") val lastName: String = "",
    @ColumnInfo(name = "contact_reference") val reference: String = "",
)