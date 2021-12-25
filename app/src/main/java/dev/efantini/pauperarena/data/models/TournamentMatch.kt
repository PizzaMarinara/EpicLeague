package dev.efantini.pauperarena.data.models

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import io.objectbox.relation.ToOne

@Entity
data class TournamentMatch(
    @Id
    var id: Long = 0,
    var player1Points: Int = 0,
    var player2Points: Int = 0
) {
    lateinit var tournamentRound: ToOne<TournamentRound>
    lateinit var player1: ToOne<TournamentPlayer>
    lateinit var player2: ToOne<TournamentPlayer>

    fun getWinner(): TournamentPlayer? {
        return when {
            player1Points > player2Points -> player1.target
            player1Points < player2Points -> player2.target
            else -> null
        }
    }
}
