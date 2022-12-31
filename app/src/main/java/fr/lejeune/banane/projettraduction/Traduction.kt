package fr.lejeune.banane.projettraduction

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "TraductionTable")
data class Traduction(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val anglais: String,
    val français: String,
    val espagnol: String,
    val allemand: String,

)


