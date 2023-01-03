package fr.lejeune.banane.projettraduction

import android.content.Context
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Traduction::class, Dict::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun traductionDao(): TraductionDao
    abstract fun dictionnaires(): DictDao

    companion object {
        @Volatile
        private var instance: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            if (instance != null)
                return instance!!
            val db = Room.databaseBuilder(context.applicationContext, AppDatabase::class.java, "Traduction")
                .fallbackToDestructiveMigration().build()
            instance = db
            return instance!!
        }
    }
}
