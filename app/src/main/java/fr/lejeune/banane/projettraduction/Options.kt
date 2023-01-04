package fr.lejeune.banane.projettraduction

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.BaseTransientBottomBar.LENGTH_SHORT
import fr.lejeune.banane.projettraduction.databinding.ActivityMainBinding
import fr.lejeune.banane.projettraduction.databinding.OptionLayoutBinding

class Options : AppCompatActivity() {

    private lateinit var binding: OptionLayoutBinding // view binding object
    private val sharedPref by lazy { getPreferences(MODE_PRIVATE) }
    private lateinit var adapter: MyRecycleAdapterDict
    val model by lazy { ViewModelProvider(this).get(AppDatabaseViewModel::class.java) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = OptionLayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.motsParJour.minValue = 0
        binding.motsParJour.maxValue = 100
        binding.motsParJour.value = sharedPref.getInt("motsParJour", 10)

        binding.frequence.minValue = 0
        binding.frequence.maxValue = 100
        binding.frequence.value = sharedPref.getInt("freq", 1)

        binding.heure.minValue = 0
        binding.heure.maxValue = 23
        binding.heure.value = sharedPref.getInt("heure", 14)

        adapter = MyRecycleAdapterDict(emptyList<Dict>().toMutableList())
        binding.recyclerDict.layoutManager = LinearLayoutManager(this)
        binding.recyclerDict.adapter = adapter
        model.getAllDicts().observe(this) {
            adapter.setListContent(it)
            adapter.notifyDataSetChanged()
        }

        binding.savebutton.setOnClickListener {
            with (sharedPref.edit()) {
                putInt("motsParJour", binding.motsParJour.value)
                putInt("freq", binding.frequence.value)
                putInt("heure", binding.heure.value)
                apply()
                showToast()
            }
        }
    }

    private fun showToast() = Toast.makeText(this, "Preferences saved.", Toast.LENGTH_SHORT).show()
}