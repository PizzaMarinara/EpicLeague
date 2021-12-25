package dev.efantini.pauperarena.data.models

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id

@Entity
data class Deck(
    @Id var id: Long = 0,
    val playerOwnerId: Long,
    val name: String = ""
)
