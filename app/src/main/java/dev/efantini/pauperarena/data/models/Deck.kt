package dev.efantini.pauperarena.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Deck(
    @PrimaryKey(autoGenerate = true) val deckId: Long = 0,
    val playerOwnerId: Long,
    val name: String = ""
)
