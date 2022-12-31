package fr.lejeune.banane.projettraduction

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.room.Room
import fr.lejeune.banane.projettraduction.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate( layoutInflater )
        setContentView( binding.root)

        val database = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "app_database"
        ).build()
        val userDao = database.traductionDao()
    }
}