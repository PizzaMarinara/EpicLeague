package dev.efantini.pauperarena.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class TournamentMatch(
    @PrimaryKey(autoGenerate = true) val id: Long,
    val score: Pair<Pair<TournamentPlayer, Int>, Pair<TournamentPlayer, Int>>,
) {
    val players: List<TournamentPlayer>
        get() { return listOf(score.first.first, score.second.first) }

    fun getWinner(): TournamentPlayer? {
        return when {
            score.first.second > score.second.second -> score.first.first
            score.first.second < score.second.second -> score.second.first
            else -> null
        }
    }
}
