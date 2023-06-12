package com.liubka.test.repository

import com.liubka.test.database.Object


interface MyRepository {

    fun saveObject(obj: Object)

    fun getObjects(): List<Object>
}