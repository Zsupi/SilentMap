package hu.bme.aut.silentmap2_1_0.logic.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import hu.nemeth.android.silentmap.MainActivity
import hu.nemeth.android.silentmap.R

class NotificationCreator(private val channelId: String,
                          private val notificationId: Int,
                          context: Context) : ContextWrapper(context) {

    private var notificationIntent: Intent
    private var text = "Undefined"
    private val enabledNotification: Boolean

    init {
        notificationIntent = Intent(context, MainActivity::class.java)
        notificationIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
        val pref = context.getSharedPreferences("AppSettings", Context.MODE_PRIVATE)
        enabledNotification = pref.getBoolean("Notification", false)
    }

    fun create(name: String, descriptionText: String, text: String){
        if (!enabledNotification)
            return

        if (this.text == "Undefined")
            this.text = text
        createNotificationChannel(name, descriptionText)

        val builder = createNotification(this.text)

        with(NotificationManagerCompat.from(this)) {
            notify(notificationId, builder.build())
        }
    }

    fun updateNotification(text: String){
        if (!enabledNotification)
            return

        this.text = text
        val builder = createNotification(this.text)
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        with(NotificationManagerCompat.from(this)) {
            notify(notificationId, builder.build())
        }
    }

    private fun createNotification(text: String): NotificationCompat.Builder {
        return NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.mipmap.ic_launcher_notification)
            .setContentTitle("SilentMap")
            .setContentText(text)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setCategory(NotificationCompat.CATEGORY_EVENT)
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
    }

    private fun createNotificationChannel(name: String, descriptionText: String) {
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(channelId, name, importance).apply {
            description = descriptionText
        }

        val notificationManager: NotificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)

    }
}