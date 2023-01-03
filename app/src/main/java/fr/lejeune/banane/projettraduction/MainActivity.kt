package fr.lejeune.banane.projettraduction

import android.R
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.core.app.NotificationCompat
import androidx.room.Insert
import androidx.room.Room
import fr.lejeune.banane.projettraduction.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding // view binding object

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

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

        binding.spinnerLanguages1.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                // do something when an item is selected
            }


            override fun onNothingSelected(parent: AdapterView<*>?) {
                // do something when nothing is selected
            }
        }
        binding.spinnerLanguages2.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                // do something when an item is selected
            }


            override fun onNothingSelected(parent: AdapterView<*>?) {
                // do something when nothing is selected
            }
        }

        binding.boutonTraduire.setOnClickListener(){
            fun checkWordInDAO(word: String, textView: TextView) {
                val language = binding.spinnerLanguages2.onItemSelectedListener.toString()
                // Check if the word has an equivalent in the selected language in the DAO
                val translation = database.traductionDao().findSpecificTraduction(word, language)
                if (translation != null) {
                    // If the word has an equivalent, set the translation in the TextView
                    textView.text = translation.toString()
                } else {
                    // If the word does not have an equivalent, display a notification
                    val notificationIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.wordreference.com/"))
                    val pendingIntent = PendingIntenCompat.Builder(this, CHANNEL_ID)
                        .setSmallIcon(R.drawable.ic_notification)
                        .setContentTitle("Word not found")
                        .setContentText("Search for the translation on WordReference")
                        .setContentIntent(pendingIntent)
                        .setAutoCancel(true)
                        .build()
                    val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
                    notificationManager.notify(NOTIFICATION_ID, notification)
                }
            }
        }


        }
    }


}