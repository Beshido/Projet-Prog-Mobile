package fr.lejeune.banane.projettraduction

import android.app.Application

class App: Application() {

    val database by lazy { AppDatabase.getDatabase(this) }
}