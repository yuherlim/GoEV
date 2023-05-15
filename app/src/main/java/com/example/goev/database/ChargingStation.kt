package com.example.goev.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "charging_station_table")
data class ChargingStation(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
    val address: String,
)