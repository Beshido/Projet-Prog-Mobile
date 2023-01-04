package fr.lejeune.banane.projettraduction

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import fr.lejeune.banane.projettraduction.databinding.ActivityMainMenuBinding
import java.util.*


class MainMenu : AppCompatActivity(){
    private lateinit var binding: ActivityMainMenuBinding // view binding object
    val notificationManager by lazy { getSystemService(NOTIFICATION_SERVICE) as NotificationManager }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityMainMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // set up click listeners for the buttons
        binding.buttonGame.setOnClickListener {
            setAlarm()
        }

        binding.buttonTranslation.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        binding.buttonOptions.setOnClickListener {
            // start the options activity
            //val intent = Intent( this,OptionActivity::class.java)
            //startActivity(Intent)
        }

    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun setAlarm() {
        println("setAlarm()")

        val sec = 2

        val intent = Intent(this, MyService::class.java)
        //intent.putExtra(MyService.MEDIA_STATE, MyService.START)
        val flag = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            PendingIntent.FLAG_IMMUTABLE
        else
            PendingIntent.FLAG_UPDATE_CURRENT

        /* création de pendingIntent avec le intent dedans */
        val pendingIntent =
            PendingIntent.getService(this, 0, intent, flag)

        /* récupérer la référence vers AlarmManager */
        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager

        /* alarm sera déclenché dans sec secondes (à peu près, ce n'est pas une alarme exacte */
        alarmManager.set(
            AlarmManager.ELAPSED_REALTIME_WAKEUP,
            SystemClock.elapsedRealtime() + sec * 1000,
            pendingIntent
        )
        alarmManager.setInexactRepeating(
            AlarmManager.ELAPSED_REALTIME_WAKEUP,
            SystemClock.elapsedRealtime() + sec * 1000,
            2000,
            pendingIntent
        )
    }

    /* les notifications doivent posséder un channel */
    private fun createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                "CHANNEL_ID",
                "private channel",
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply { description = "private channel" }

            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            notificationManager.createNotificationChannel(channel)
        }
    }

}

