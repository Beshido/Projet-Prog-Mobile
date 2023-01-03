package fr.lejeune.banane.projettraduction

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import fr.lejeune.banane.projettraduction.databinding.ActivityMainMenuBinding



class MainMenu : AppCompatActivity(){
    private lateinit var binding: ActivityMainMenuBinding // view binding object
    override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityMainMenuBinding.inflate(layoutInflater)
    setContentView(binding.root)

    // set up click listeners for the buttons
    binding.buttonGame.setOnClickListener {
        // start the game activity
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
}

