package fr.lejeune.banane.projettraduction

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.*

@Dao
interface DictDao {
    @Insert
    fun insert(dict: Dict)

    @Insert(entity = Dict::class, onConflict = OnConflictStrategy.ABORT)
    fun insertItem(dictItem: DictItem)

    @Update
    fun update(dict: Dict)

    @Delete
    fun delete(dict: Dict)

    @Query("SELECT * FROM DictTable")
    fun getAll(): LiveData<List<Dict>>

    @Query("SELECT * FROM DictTable WHERE language_source = :source AND language_dest = :dest ")
    fun getSpecificDicts(source: String, dest: String): LiveData<List<Dict>>
}
