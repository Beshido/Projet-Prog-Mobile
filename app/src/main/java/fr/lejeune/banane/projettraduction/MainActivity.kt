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
import androidx.core.view.get
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Insert
import androidx.room.Room
import fr.lejeune.banane.projettraduction.databinding.ActivityMainBinding
import java.net.URI

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding // view binding object
    val notificationManager by lazy { getSystemService(NOTIFICATION_SERVICE) as NotificationManager }

    val model by lazy { ViewModelProvider(this).get(AppDatabaseViewModel::class.java) }

    private lateinit var adapter: MyRecycleAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // createNotificationChannel()

        if (savedInstanceState == null)
            model.getSpecificDicts(model.languageOptions[0], model.languageOptions[1])


        adapter = MyRecycleAdapter(emptyList<Dict>().toMutableList())
        binding.dictSpinner.layoutManager = LinearLayoutManager(this)
        binding.dictSpinner.adapter = adapter
        model.resultatSelect?.observe(this) {
            adapter.setListContent(it)
            adapter.notifyDataSetChanged()
        }

        val adapterLangues = ArrayAdapter(this, R.layout.simple_spinner_item, model.languageOptions)
        adapterLangues.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerLanguages1.adapter = adapterLangues
        binding.spinnerLanguages2.adapter = adapterLangues
        binding.spinnerLanguages1.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                model.l1 = position
                model.resultatSelect?.removeObservers( this@MainActivity )
                model.getSpecificDicts(model.languageOptions[model.l1], model.languageOptions[model.l2])
                model.resultatSelect?.observe( this@MainActivity ) {
                    adapter.setListContent(it)
                    adapter.notifyDataSetChanged()
                }
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
        binding.spinnerLanguages2.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                model.l2 = position

                model.resultatSelect?.removeObservers( this@MainActivity )
                model.getSpecificDicts(model.languageOptions[model.l1], model.languageOptions[model.l2])
                model.resultatSelect?.observe( this@MainActivity ) {
                    adapter.setListContent(it)
                    adapter.notifyDataSetChanged()
                }
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        binding.boutonTraduire.setOnClickListener {
            val word = binding.motAtraduireEditText.text.toString()
            val language = model.languageOptions.get(model.l2)
            val dict = if (!adapter.getSelected()?.url.isNullOrEmpty()) adapter.getSelected()?.url else "https://www.google.fr/search?q=traduction+$language+"

            val url = dict + word

            // Check if the word has an equivalent in the selected language in the DAO
            Thread {
                val intent = Intent(Intent.ACTION_VIEW)
                intent.setData(Uri.parse(url))
                startActivity(intent)
            }.start()

        }

        binding.boutonAjoutBDD.setOnClickListener {
            val word = binding.motAtraduireEditText.text.toString()
            val language_source = model.languageOptions.get(model.l1)
            val language_dest = model.languageOptions.get(model.l2)
            val dict = model.url

            if (word.isNullOrEmpty() || model.l1 == model.l2 || model.url.isNullOrEmpty()) {
                Toast.makeText(this, "Erreur...", Toast.LENGTH_SHORT).show()
            }
            else {
                Toast.makeText(this, "Ajout du mot $word à la base de donnée...", Toast.LENGTH_SHORT).show()
                Thread {
                    model.addTraduction(TraductionItem(word, language_source, language_dest, dict!!))
                    model.addDict(DictItem(dict, language_source, language_dest))
                }.start()
            }

        }


        if(intent.action.equals("android.intent.action.SEND")) {
            intent.getStringExtra("android.intent.extra.TEXT").let {
                println(it)
                if (it != null) {
                    val lastSlashIndex = it.lastIndexOf("/")

                    // valeurs de départ
                    var indexBegin = lastSlashIndex + 1
                    var indexEnd = it.length
                    if (it.contains("larousse")) {
                        indexBegin = 55
                        indexEnd = lastSlashIndex
                    }

                    model.url = it.substring(0, indexBegin)
                    val word = it.substring(indexBegin, indexEnd)

                    binding.motAtraduireEditText.setText(word)

                    println(model.url)
                    println(word)
                }
            }
        }
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                "CHANNEL_ID",
                "private channel",
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply { description = "private channel" }

            notificationManager.createNotificationChannel(channel)
        }
    }
}