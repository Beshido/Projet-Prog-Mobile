package fr.lejeune.banane.projettraduction

import androidx.room.Dao
import androidx.room.*

@Dao
interface TraductionDao {
    @Insert
    fun insert(traduction: Traduction)

    @Update
    fun update(traduction: Traduction)

    @Delete
    fun delete(traduction: Traduction)

    @Query("SELECT * FROM TraductionTable")
    fun getAll(): List<Traduction>

    @Query("SELECT * FROM TraductionTable WHERE id = :id")
    fun findById(id: Int): Traduction

    @Query("Select * FROM TraductionTable WHERE base_language or target_language = :word")
    fun findTraduction(word: String): Traduction

    @Query("Select * FROM TraductionTable WHERE base_language = :language AND target_language = :word")
    fun findSpecificTraduction(word: String, language: String): Traduction
}