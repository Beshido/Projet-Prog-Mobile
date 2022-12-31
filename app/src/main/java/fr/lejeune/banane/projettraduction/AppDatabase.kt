package fr.lejeune.banane.projettraduction

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Traduction::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun traductionDao(): TraductionDao
}
