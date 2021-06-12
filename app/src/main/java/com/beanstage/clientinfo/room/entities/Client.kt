package com.beanstage.clientinfo.room.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "client_table")
data class Client(
    @PrimaryKey @ColumnInfo(name = "client_name") val clientName: String,
    @ColumnInfo(name = "social_reason") val socialReason: String = "",
    @ColumnInfo(name = "contact_agent") val contactAgent: String = "",
)