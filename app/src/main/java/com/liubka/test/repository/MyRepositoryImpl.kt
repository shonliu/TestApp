package com.liubka.test.repository

import com.liubka.test.database.Object
import com.liubka.test.database.ObjectDao
import javax.inject.Inject

class MyRepositoryImpl @Inject constructor(
    private val objectDao: ObjectDao
) : MyRepository {

    override fun saveObject(obj: Object) = objectDao.insertAll(obj)


    override fun getObjects(): List<Object> = objectDao.getAll()

}