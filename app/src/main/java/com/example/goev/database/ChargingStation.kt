package com.example.goev.database

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize


@Parcelize
@Entity(tableName = "charging_station_table")
data class ChargingStation(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
    val address: String,
): Parcelable