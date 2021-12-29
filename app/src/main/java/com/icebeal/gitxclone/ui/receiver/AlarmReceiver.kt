package com.icebeal.gitxclone.ui.receiver

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.content.getSystemService
import com.icebeal.gitxclone.R
import com.icebeal.gitxclone.ui.activity.MainActivity
import java.util.*

class AlarmReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {

        val message = context.getString(R.string.alarm_msg)

        sendNotification(context, message)

    }

    fun set(context: Context, state:Boolean){

        if(state){

            setReminder((context))

        } else {

            cancelReminder(context)

        }

    }

    private fun setReminder(context: Context){

        val alarmManager = context.getSystemService<AlarmManager>()

        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, 9)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)

        val intent = Intent(context, AlarmReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(context, NOTIF_ID, intent, 0)

        alarmManager?.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, AlarmManager.INTERVAL_DAY, pendingIntent)

    }

    private fun cancelReminder(context: Context){

        val alarmManager = context.getSystemService<AlarmManager>()

        val intent = Intent(context, AlarmReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(context, NOTIF_ID, intent, 0)
        pendingIntent.cancel()

        alarmManager?.cancel(pendingIntent)

    }

    private fun sendNotification(context: Context, message:String){

        val intent = Intent(context, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP

        val pendingIntent = PendingIntent.getActivity(context, NOTIF_ID, intent, PendingIntent.FLAG_UPDATE_CURRENT)

        val notificationManager = context.getSystemService<NotificationManager>()
        val alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)

        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setContentTitle(context.getString(R.string.notif_title))
            .setContentText(message)
            .setSmallIcon(R.drawable.ic_baseline_timer_24)
            .setSound(alarmSound)
            .setVibrate(longArrayOf(1000, 1000, 1000, 1000, 1000))
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){

            val channel = NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT)
            channel.enableVibration(true)
            channel.vibrationPattern = longArrayOf(1000, 1000, 1000, 1000, 1000)

            builder.setChannelId(CHANNEL_ID)

            notificationManager?.createNotificationChannel(channel)

        }

        val notification = builder.build()

        notificationManager?.notify(NOTIF_ID, notification)

    }

    companion object{

        private const val CHANNEL_ID = "channel_01"
        private const val CHANNEL_NAME = "alarm_channel"
        private const val NOTIF_ID = 30

    }
}