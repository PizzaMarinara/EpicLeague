package dev.efantini.pauperarena.data.models

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id

@Entity
data class TournamentRound(
    @Id var id: Long = 0,
    val matches: List<TournamentMatch>
)
