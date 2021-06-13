package com.beanstage.clientinfo.room.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "address_table")
data class Address (
   @PrimaryKey(autoGenerate = true) val addressId: Long? = null,
    val clientName: String,
    @ColumnInfo(name = "sector_name") val sectorName: String,
    @ColumnInfo(name = "street_name") val streetName: String = "",
    @ColumnInfo(name = "number") val number: String = "",
    @ColumnInfo(name = "reference") val reference: String = "",
)