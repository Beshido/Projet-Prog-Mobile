package fr.lejeune.banane.projettraduction

import android.app.*
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Build
import android.os.IBinder
import android.preference.PreferenceManager
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.lifecycle.MutableLiveData


class MyService : Service() {
    private val channelId = "message urgent"
    private val notificationManager by lazy { getSystemService(NOTIFICATION_SERVICE) as NotificationManager }
    private val dao by lazy { AppDatabase.getDatabase(this).traductionDao() }
    private var values = MutableLiveData<List<Traduction>>()
    private val pendingFlag = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) PendingIntent.FLAG_IMMUTABLE else PendingIntent.FLAG_UPDATE_CURRENT
    private val preferences by lazy { getSharedPreferences("Options", MODE_PRIVATE) }

    override fun onBind(intent: Intent): IBinder? {
        TODO()
    }

    override fun onCreate(){
        super.onCreate()
        createNotificationChannel()
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        if(intent.action == "stop"){
            stopSelf()
            return START_NOT_STICKY
        }

        if(!intent.action.equals("start")){
            Log.d("MyService", "action inconnue")
            return START_NOT_STICKY
        }

        Thread {
            values.postValue(dao.getRandomTraductions(preferences.getInt("motsParJour", 10)))
        }.start()

        values.observeForever {
            for (i in it.indices) {
                var isNotificationExists = false
                for (j in notificationManager.activeNotifications.indices) {
                    if (notificationManager.activeNotifications[i].id == it[i].id) {
                        isNotificationExists = true
                    }
                }
                if (isNotificationExists) {
                    continue
                }

                val intentAction = Intent(Intent.ACTION_VIEW)
                intentAction.data = Uri.parse(it[i].dict)

                fun getPendingEvent(): PendingIntent {
                    val pendingIntent = PendingIntent.getActivity(this, i, intentAction, pendingFlag)
                    it[i].score -= 2
                    Thread {
                        dao.update(it[i])
                    }.start()
                    return pendingIntent
                }

                val notification = NotificationCompat.Builder(this, channelId)
                    .setContentTitle(it[i].word)
                    .setContentText(it[i].base_language + " -> " + it[i].target_language)
                    .setSmallIcon(R.drawable.logo_petit)
                    .addAction(R.drawable.logo_petit, "Voir la traduction", getPendingEvent())
                    .build()

                it[i].score += 1
                Thread {
                    dao.update(it[i])
                }.start()

                notificationManager.notify(it[i].id, notification)
            }
        }

        return START_NOT_STICKY
    }

    override fun onDestroy() {
        Log.d("MyService", "OnDestroy")
        super.onDestroy()
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(channelId, "private channel", NotificationManager.IMPORTANCE_DEFAULT)
                .apply { description = "private channel" }
            notificationManager.createNotificationChannel(channel)
        }
    }
}


