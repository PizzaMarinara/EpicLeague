package dev.efantini.pauperarena.data.models

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id

@Entity
data class TournamentPlayer(
    @Id var id: Long = 0,
    val playerId: Long,
    val deckId: Long,
    val signedUpTournamentId: Long
)
