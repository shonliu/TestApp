package com.liubka.test.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.sql.Timestamp

@Entity
data class Object(
    @PrimaryKey() val uid: Int,
    @ColumnInfo(name = "timestamp_of_the_boot_event") val timestamp: Long,
)