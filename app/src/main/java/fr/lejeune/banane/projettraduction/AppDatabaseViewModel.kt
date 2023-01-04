package fr.lejeune.banane.projettraduction

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel

class AppDatabaseViewModel(application: Application): AndroidViewModel(application) {
    val traductionDao = (application as App).database.traductionDao()
    val dictDao = (application as App).database.dictDao()

    fun findTranslation(word: String, language: String) = traductionDao.findSpecificTraduction(word, language)
    fun getAllDicts() = dictDao.getAll()
}
