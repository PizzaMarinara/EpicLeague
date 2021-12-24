package dev.efantini.pauperarena.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class Card(
    @PrimaryKey val cardId: String,
    val oracleId: String,
    val name: String,
    val image: String? = null,
    val imageList: String? = null,
    val manaCost: String? = null,
    val cmc: Long? = null,
    val typeLine: String? = null,
    val oracleText: String? = null,
    val colors: String? = null,
    val colorIdentity: String? = null,
    val keywords: String? = null
)
