package dev.efantini.pauperarena.data.models

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Relation

@Entity
data class TournamentPlayer(
    @Embedded val player: Player,
    @Relation(
        parentColumn = "playerId",
        entityColumn = "playerOwnerId"
    )
    val deck: Deck,
    val signedUpTournamentId: Long
)
