package fr.lejeune.banane.projettraduction

import androidx.room.Dao
import androidx.room.*

@Dao
interface DictDao {
    @Insert
    fun insert(dict: Dict)

    @Update
    fun update(dict: Dict)

    @Delete
    fun delete(dict: Dict)

    @Query("SELECT * FROM DictTable")
    fun getAll(): List<Dict>
}
