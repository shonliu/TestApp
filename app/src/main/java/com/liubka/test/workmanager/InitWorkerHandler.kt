package com.liubka.test.workmanager

import android.content.Context
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import dagger.hilt.android.qualifiers.ApplicationContext
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class InitWorkerHandler @Inject constructor(@ApplicationContext private val context: Context) {

    fun initWorker() {
        val work = PeriodicWorkRequestBuilder<InitWorker>(15, TimeUnit.MINUTES)
            .build()

        WorkManager.getInstance(context).enqueueUniquePeriodicWork(
            "InitWorker",
            ExistingPeriodicWorkPolicy.UPDATE,
            work
        )
    }
}