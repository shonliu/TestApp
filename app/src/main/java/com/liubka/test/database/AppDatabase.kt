package com.liubka.test.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.liubka.test.database.Object
import com.liubka.test.database.ObjectDao

@Database(entities = [Object::class], version = 1, exportSchema = false)
abstract class AppDatabase: RoomDatabase() {
    abstract fun objDao(): ObjectDao
}