package fr.lejeune.banane.projettraduction

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "DictTable")
data class Dict(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val url: String,
    val language_source: String,
    val language_dest: String
)