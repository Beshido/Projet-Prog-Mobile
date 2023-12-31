package fr.lejeune.banane.projettraduction

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "TraductionTable")
data class Traduction(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val word: String,
    val base_language: String,
    val target_language: String,
    val dict: String,
    var score: Int,
)


