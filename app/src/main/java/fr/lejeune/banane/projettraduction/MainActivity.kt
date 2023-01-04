package fr.lejeune.banane.projettraduction

import android.R
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.room.Dao
import androidx.core.app.NotificationCompat
import androidx.lifecycle.ViewModelProvider
import androidx.room.Insert
import androidx.room.Room
import fr.lejeune.banane.projettraduction.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding // view binding object
    val notificationManager by lazy { getSystemService(NOTIFICATION_SERVICE) as NotificationManager }

    val model by lazy { ViewModelProvider(this).get(AppDatabaseViewModel::class.java) }

    private lateinit var adapter: MyRecycleAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        createNotificationChannel()

        model.getAllDicts()

        adapter = MyRecycleAdapter(emptyList<Dict>().toMutableList())
        binding.dictSpinner.adapter = adapter

        val languageOptions = arrayOf("Français", "Anglais", "Espagnol", "Allemand")
        val adapterLangues = ArrayAdapter(this, R.layout.simple_spinner_item, languageOptions)
        adapterLangues.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerLanguages1.adapter = adapterLangues
        binding.spinnerLanguages2.adapter = adapterLangues

        binding.boutonTraduire.setOnClickListener {
            val word = binding.motAtraduireEditText.toString()
            val language = binding.spinnerLanguages2.onItemSelectedListener.toString()
            // Check if the word has an equivalent in the selected language in the DAO
            Thread {
                val intent = Intent(Intent.ACTION_VIEW)
                intent.setData(Uri.parse("https://www.wordreference.com/fren/manger"))
                startActivity(intent)
            }.start()

        }

        binding.boutonAjoutBDD.setOnClickListener {
        }
    }

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