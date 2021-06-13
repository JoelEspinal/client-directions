package com.beanstage.clientinfo.room.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "address_table")
data class Address (
    @PrimaryKey(autoGenerate = true) var addressId: Long? = null,
    val clientId: Long,
    @ColumnInfo(name = "sector_name") var sectorName: String,
    @ColumnInfo(name = "street_name") var streetName: String = "",
    @ColumnInfo(name = "number") var number: String = "",
    @ColumnInfo(name = "reference") var reference: String = "",
)