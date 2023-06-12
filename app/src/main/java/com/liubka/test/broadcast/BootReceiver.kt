package com.liubka.test.broadcast

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class BootReceiver @Inject constructor(): BroadcastReceiver() {

    @Inject
    lateinit var bootHandler: BootReceiverHandler

    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent?.action.equals(Intent.ACTION_BOOT_COMPLETED)) {
            bootHandler.handle()
        }
    }
}