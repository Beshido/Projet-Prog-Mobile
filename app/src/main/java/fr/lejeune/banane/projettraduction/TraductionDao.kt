package fr.lejeune.banane.projettraduction

import androidx.room.Dao
import androidx.room.*

@Dao
interface TraductionDao {
    @Insert(entity = Traduction::class, onConflict = OnConflictStrategy.ABORT)
    fun insert(traduction: TraductionItem)

    @Update
    fun update(traduction: Traduction): Int

    @Delete
    fun delete(traduction: Traduction)

    @Query("SELECT * FROM TraductionTable")
    fun getAll(): List<Traduction>

    @Query("SELECT * FROM TraductionTable WHERE id = :id")
    fun findById(id: Int): Traduction

    @Query("SELECT * FROM TraductionTable WHERE base_language or target_language = :word")
    fun findTraduction(word: String): Traduction

    @Query("SELECT * FROM TraductionTable WHERE base_language = :language AND target_language = :word")
    fun findSpecificTraduction(word: String, language: String): Traduction

    @Query ("SELECT * FROM TraductionTable WHERE score <= 3 ORDER BY RANDOM() LIMIT :n")
    fun getRandomTraductions(n: Int): List<Traduction>
}