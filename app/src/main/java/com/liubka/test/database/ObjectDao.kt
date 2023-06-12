package com.liubka.test.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ObjectDao {

    @Query("SELECT * FROM object")
    fun getAll(): List<Object>

    @Insert
    fun insertAll(vararg objects: Object)

    @Delete
    fun delete(user: Object)

}