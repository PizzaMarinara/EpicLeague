package dev.efantini.pauperarena.data.models

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id

@Entity
class Card(
    @Id var id: Long = 0,
    val scryfallId: String,
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
