package com.example.goev.databaseChargingStation

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.example.goev.database.user.UserData

@Suppress("ArrayInDataClass")
@Entity(
    tableName = "charging_station_table",
    foreignKeys = [ForeignKey(
        entity = UserData::class,
        parentColumns = ["id"],
        childColumns = ["userId"]
    )]
)
data class ChargingStation(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val userId: Int,
    val name: String,
    val address: String,
    val image: ByteArray?,
)