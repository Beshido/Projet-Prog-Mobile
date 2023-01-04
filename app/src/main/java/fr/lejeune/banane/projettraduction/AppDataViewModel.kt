package fr.lejeune.banane.projettraduction

import android.app.Application
import androidx.lifecycle.AndroidViewModel

class AppDatabaseViewModel(application: Application): AndroidViewModel(application) {
    val traductionDao = (application as App).database.traductionDao()

    fun findTranslation(word: String, language: String) = traductionDao.findSpecificTraduction(word, language)
}
