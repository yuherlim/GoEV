package com.example.goev

import androidx.room.TypeConverter
import androidx.room.TypeConverters
import java.util.*

@TypeConverters
public object DateConverters {
    // convert long to date
    @JvmStatic
    @TypeConverter
    fun fromDate(date: Date?): Long? {
        return date?.time
    }

    // convert date to long
    @JvmStatic
    @TypeConverter
    fun toDate(timeStamp: Long?): Date? {
        return if (timeStamp == null) null else Date(timeStamp)
    }
}