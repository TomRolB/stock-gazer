package com.example.stockgazer

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ProcessLifecycleOwner
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.example.stockgazer.notification.NotificationReceiver
import com.example.stockgazer.notification.notificationChannelID

@HiltAndroidApp
class StockGazerApplication : Application(), LifecycleObserver {
    override fun onCreate() {
        super.onCreate()
        ProcessLifecycleOwner.get().lifecycle.addObserver(this)
        createNotificationChannel()
    }

    private fun createNotificationChannel() {
        val channel = NotificationChannel(
            notificationChannelID,
            "Stock Updates",
            NotificationManager.IMPORTANCE_HIGH
        ).apply { description = "Channel for stock update notifications" }
        getSystemService(NotificationManager::class.java)
            .createNotificationChannel(channel)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun onAppBackgrounded() {
        scheduleStockNotificationAfterDelay(3000L)
    }

    private fun scheduleStockNotificationAfterDelay(delayMs: Long) {
        val intent = Intent(this, NotificationReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(
            this,
            0,
            intent,
            PendingIntent.FLAG_IMMUTABLE
        )
        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            System.currentTimeMillis() + delayMs,
            pendingIntent
        )
    }
}