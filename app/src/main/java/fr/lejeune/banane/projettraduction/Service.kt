package fr.lejeune.banane.projettraduction

import android.app.*
import android.content.Intent
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat


@RequiresApi(Build.VERSION_CODES.M)
class MyService : Service() {
    private val CHANNEL_ID = "channel"
    val notificationManager by lazy { getSystemService(NotificationManager::class.java) }

    override fun onBind(intent: Intent): IBinder? {
        TODO()
    }

    override fun onCreate(){
        super.onCreate()
        createNotificationChannel()
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, "channel_name", importance)
            channel.description = "channel_description"
            notificationManager.createNotificationChannel(channel)
        }
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        if( intent.action == "stop" ){
            stopSelf() // arrêter le service lui-même
            return START_NOT_STICKY
        }

        if( !intent.action.equals("start") ){
            Log.d("MyService", "l'action inconnue")
            return START_NOT_STICKY
        }
        Log.d("MyService", "GO GO GO")

        /* quand l'utilisateur clique sur le bouton de l'alarme le intent suivant sera envoyé vers le service */
        val serviceIntent = Intent(this, MyService::class.java).apply{
            action="stop"
        }
        val servicePendingIntent = PendingIntent.getService(this, 1, serviceIntent, PendingIntent.FLAG_IMMUTABLE)

        /* intent envoyé quand l'utilisateur touche sur la notification */
        val activityPendingIntent = PendingIntent.getActivity(this, 1,
            Intent(this,
                MainActivity::class.java),
            PendingIntent.FLAG_IMMUTABLE)

        val notification: Notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("musique")
            .setContentText("jouer la musique")
            .setSmallIcon(R.drawable.logo)
            .setContentIntent( activityPendingIntent ) /* activer une activité */
            .addAction(R.drawable.logo, "arrêter la musique", servicePendingIntent)
            .setAutoCancel(true)
            .build()

        Log.d("MyService", "passer en mode foreground")
        startForeground(1, notification)

        if( intent.data == null )
            return START_NOT_STICKY

        return START_NOT_STICKY
    }

    override fun onDestroy() {
        Log.d("MyService", "OnDestroy")
        super.onDestroy()
    }
}


