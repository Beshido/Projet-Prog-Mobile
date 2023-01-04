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

private lateinit var dao: Dao

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding // view binding object
    val CHANNEL_ID = "message urgent"
    val notificationManager by lazy { getSystemService(NOTIFICATION_SERVICE) as NotificationManager }
    val pendingFlag = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) PendingIntent.FLAG_IMMUTABLE else PendingIntent.FLAG_UPDATE_CURRENT

    val model by lazy { ViewModelProvider(this).get(AppDatabaseViewModel::class.java) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        createNotificationChannel()

        val database = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "app_database"


        ).build()
        val userDao = database.traductionDao()
        val languageOptions = arrayOf("Français", "Anglais", "Espagnol", "Allemand")
        val adapter = ArrayAdapter(this, R.layout.simple_spinner_item, languageOptions)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerLanguages1.adapter = adapter
        binding.spinnerLanguages2.adapter = adapter

        binding.spinnerLanguages1.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    // do something when an item is selected
                }


                override fun onNothingSelected(parent: AdapterView<*>?) {
                    // do something when nothing is selected
                }
            }
        binding.spinnerLanguages2.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    // do something when an item is selected
                }


                override fun onNothingSelected(parent: AdapterView<*>?) {
                    // do something when nothing is selected
                }
            }
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
                CHANNEL_ID,
                "private channel",
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply { description = "private channel" }

            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            notificationManager.createNotificationChannel(channel)


        }
    }
}