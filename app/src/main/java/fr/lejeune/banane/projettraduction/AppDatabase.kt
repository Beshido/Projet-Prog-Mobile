package fr.lejeune.banane.projettraduction

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

class TraductionItem(
    val word: String,
    val base_language: String,
    val target_language: String,
    val dict: String
)

class DictItem(
    val url: String,
    val language_source: String,
    val language_dest: String
)

@Database(entities = [Traduction::class, Dict::class], version = 17)
abstract class AppDatabase : RoomDatabase() {
    abstract fun traductionDao(): TraductionDao
    abstract fun dictDao(): DictDao

    //create function getDatabase
    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "DictTable"
                ).fallbackToDestructiveMigration().build()
                INSTANCE = instance
                return instance
            }
        }
    }
}
