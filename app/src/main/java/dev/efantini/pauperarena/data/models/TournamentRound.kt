package dev.efantini.pauperarena.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class TournamentRound(
    @PrimaryKey(autoGenerate = true) val id: Long,
    val matches: List<TournamentMatch>
)
