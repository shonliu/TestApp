package com.liubka.test.broadcast

import com.liubka.test.repository.MyRepository
import com.liubka.test.database.Object
import com.liubka.test.workmanager.InitWorkerHandler
import kotlinx.coroutines.*
import javax.inject.Inject


class BootReceiverHandler @Inject constructor(
    private val myRepository: MyRepository,
    private val workerHandler: InitWorkerHandler,
) {

    fun handle() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val timestamp = System.currentTimeMillis()
                myRepository.saveObject(Object(timestamp.toInt(), timestamp))
                workerHandler.initWorker()
            } finally {
                cancel()
            }
        }
    }
}