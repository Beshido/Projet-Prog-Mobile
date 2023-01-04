package fr.lejeune.banane.projettraduction

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.RequiresApi
import fr.lejeune.banane.projettraduction.databinding.ActivityMainMenuBinding
import java.util.*


class MainMenu : AppCompatActivity(){
    private lateinit var binding: ActivityMainMenuBinding // view binding object
    private val preferences by lazy { getSharedPreferences("Options", MODE_PRIVATE) }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityMainMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonGame.setOnClickListener {
            setAlarm(0)
        }

        binding.buttonTranslation.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }

        binding.buttonOptions.setOnClickListener {
            startActivity(Intent(this, Options::class.java))
        }

        setAlarm(-1)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun setAlarm(sec: Int) {
        val serviceIntent = Intent(this, MyService::class.java).apply {
            action = "start"
        }

        val pendingIntent = PendingIntent.getService(this, 0, serviceIntent, PendingIntent.FLAG_IMMUTABLE)

        val alarmManager = getSystemService(ALARM_SERVICE) as AlarmManager
        val calendar: Calendar
        if (sec == -1) {
             calendar = Calendar.getInstance().apply {
                timeInMillis = System.currentTimeMillis()
                set(Calendar.HOUR_OF_DAY, preferences.getInt("heure", 14))
            }
            alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, AlarmManager.INTERVAL_DAY, pendingIntent)

        }
        else {
            calendar = Calendar.getInstance()
            calendar.add(Calendar.SECOND, sec)
            alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pendingIntent)
        }



    }
}

