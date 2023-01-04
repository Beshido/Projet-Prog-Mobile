package fr.lejeune.banane.projettraduction

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData

class AppDatabaseViewModel(application: Application): AndroidViewModel(application) {
    var l1 = 0
    var l2 = 0
    val languageOptions = arrayOf("francais", "anglais", "espagnol", "allemand")
    var base_url: String? = null
    var url: String? = null

    val traductionDao = AppDatabase.getDatabase(application).traductionDao()
    val dictDao =  AppDatabase.getDatabase(application).dictionnaireDao()

    fun findTranslation(word: String, language: String) = traductionDao.findSpecificTraduction(word, language)

    fun getAllDicts() = dictDao.getAll()
    fun getTraductions(n: Int) = traductionDao.getRandomTraductions(n)

    var resultatSelect : LiveData<List<Dict>>? = null
    fun getSpecificDicts(source: String, dest: String): LiveData<List<Dict>>? {
        resultatSelect = dictDao.getSpecificDicts(source, dest)
        return resultatSelect
    }

    fun addTraduction(trad: TraductionItem) = traductionDao.insert(trad)
    fun updateTraduction(trad: Traduction) = traductionDao.update(trad)

    fun addDict(dictItem: DictItem) = dictDao.insertItem(dictItem)
}
