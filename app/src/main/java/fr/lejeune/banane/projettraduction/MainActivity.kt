package fr.lejeune.banane.projettraduction

import android.R
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
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
        binding.spinnerLanguages.adapter = adapter

        binding.spinnerLanguages.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                // do something when an item is selected
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // do something when nothing is selected
            }
        }
    }


}