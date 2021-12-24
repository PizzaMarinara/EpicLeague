package dev.efantini.pauperarena.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Tournament(
    @PrimaryKey(autoGenerate = true) val tournamentId: Long,
    val participants: List<TournamentPlayer>,
    val rounds: List<TournamentRound>,
    val pointsAssigned: ArrayList<Int>,
    val date: String,
    val seasonId: Long?
)
