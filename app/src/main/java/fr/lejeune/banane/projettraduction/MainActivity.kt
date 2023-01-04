package fr.lejeune.banane.projettraduction

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import fr.lejeune.banane.projettraduction.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding // view binding object
    val model by lazy { ViewModelProvider(this).get(AppDatabaseViewModel::class.java) }
    private lateinit var adapter: MyRecycleAdapterDict

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null)
            model.getSpecificDicts(model.languageOptions[0], model.languageOptions[1])


        adapter = MyRecycleAdapterDict(emptyList<Dict>().toMutableList())
        binding.dictSpinner.layoutManager = LinearLayoutManager(this)
        binding.dictSpinner.adapter = adapter
        model.resultatSelect?.observe(this) {
            adapter.setListContent(it)
            adapter.notifyDataSetChanged()
        }

        val adapterLangues = ArrayAdapter(this, android.R.layout.simple_spinner_item, model.languageOptions)
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
            if (word.isNotBlank()) {
                Thread {
                    val intent = Intent(Intent.ACTION_VIEW)
                    intent.data = Uri.parse(url)
                    startActivity(intent)
                }.start()
            }


        }

        binding.boutonAjoutBDD.setOnClickListener {
            val word = binding.motAtraduireEditText.text.toString()
            val languageSource = model.languageOptions.get(model.l1)
            val languageDest = model.languageOptions.get(model.l2)
            val dict = model.url

            if (word.isEmpty() || model.l1 == model.l2 || model.url.isNullOrEmpty()) {
                Toast.makeText(this, "Erreur...", Toast.LENGTH_SHORT).show()
            }
            else {
                Toast.makeText(this, "Ajout du mot $word à la base de donnée...", Toast.LENGTH_SHORT).show()
                Thread {
                    model.addTraduction(TraductionItem(word, languageSource, languageDest, dict!!))
                    model.addDict(DictItem(dict, languageSource, languageDest))
                }.start()
            }

        }


        if(intent.action.equals("android.intent.action.SEND")) {
            intent.getStringExtra("android.intent.extra.TEXT").let {
                if (it != null) {
                    val lastSlashIndex = it.lastIndexOf("/")

                    // valeurs de départ
                    var indexBegin = lastSlashIndex + 1
                    var indexEnd = it.length
                    if (it.contains("larousse")) {
                        indexBegin = 55
                        indexEnd = lastSlashIndex
                    }

                    model.url = it
                    model.base_url = it.substring(0, indexBegin)
                    val word = it.substring(indexBegin, indexEnd)
                    binding.motAtraduireEditText.setText(word)

                    println(model.url)
                    println(word)
                }
            }
        }
    }
}