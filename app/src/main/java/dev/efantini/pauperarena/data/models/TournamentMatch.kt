package dev.efantini.pauperarena.data.models

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id

@Entity
data class TournamentMatch(
    @Id var id: Long = 0,
    val score: String
    // val score: Pair<Pair<TournamentPlayer, Int>, Pair<TournamentPlayer, Int>>,
) {
    /*
    val players: List<TournamentPlayer>
        get() { return listOf(score.first.first, score.second.first) }

    fun getWinner(): TournamentPlayer? {
        return when {
            score.first.second > score.second.second -> score.first.first
            score.first.second < score.second.second -> score.second.first
            else -> null
        }
    }
    */
}
