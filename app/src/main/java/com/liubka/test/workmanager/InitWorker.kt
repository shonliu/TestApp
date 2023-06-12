package com.liubka.test.workmanager

import android.Manifest
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import androidx.work.Worker
import androidx.work.WorkerParameters

import com.liubka.test.MainActivity
import com.liubka.test.MyApplication
import com.liubka.test.R
import com.liubka.test.database.Object
import com.liubka.test.repository.MyRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class InitWorker constructor(
    context: Context,
    workerParameters: WorkerParameters,
) : Worker(context, workerParameters) {

    override fun doWork(): Result {
        //TODO: Inject myRepository

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val myApp = applicationContext as MyApplication
                val objects = myApp.myRepository.getObjects()

                var contentText = ""

                contentText = if (objects.isEmpty()) {
                    "No boots detected"
                } else if (objects.size == 1) {
                    "The boot was detected with the timestamp = ${objects[0].timestamp}"
                } else {
                    val lastIndex = objects.lastIndex
                    val preLast = objects.lastIndex - 1

                    val delta = objects[lastIndex].timestamp - objects[preLast].timestamp
                    "Last boots time delta = $delta"
                }
                showNotification(contentText)

            } finally {
                cancel()
            }
        }


        return Result.success()
    }

    private fun showNotification(content: String) {
        val builder = NotificationCompat.Builder(applicationContext, "notify_001")
        val intent = Intent(applicationContext, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent: PendingIntent =
            PendingIntent.getActivity(applicationContext, 0, intent, PendingIntent.FLAG_IMMUTABLE)

        val title = "15 min notification"
        val smallIcon = R.drawable.baseline_notifications_24

        builder.setContentIntent(pendingIntent)
        builder.setSmallIcon(smallIcon)
        builder.setContentTitle(title)
        builder.setContentText(content)
        builder.setAutoCancel(true)


        val notificationManager =
            applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channelId = "Your_channel_id"
            val channel =
                NotificationChannel(channelId, "Test channel", NotificationManager.IMPORTANCE_HIGH)
            notificationManager.createNotificationChannel(channel)
            builder.setChannelId(channelId)
        }


        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU || ActivityCompat.checkSelfPermission(
                applicationContext,
                Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            notificationManager.notify(0, builder.build())
        }

    }
}