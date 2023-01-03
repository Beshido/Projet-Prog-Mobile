package fr.lejeune.banane.projettraduction

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "TraductionTable")
data class Traduction(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val base_language: String,
    val target_language: String,
    val translation_page: String,
)