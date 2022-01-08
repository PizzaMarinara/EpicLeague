package dev.efantini.epicleague.data.models

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
    lateinit var tournamentPlayer1: ToOne<TournamentPlayer>
    lateinit var tournamentPlayer2: ToOne<TournamentPlayer>

    fun getWinner(): TournamentPlayer? {
        return when {
            tournamentPlayer2.isNull -> tournamentPlayer1.target
            tournamentPlayer1.isNull -> tournamentPlayer2.target
            player1Points > player2Points -> tournamentPlayer1.target
            player1Points < player2Points -> tournamentPlayer2.target
            else -> null
        }
    }

    fun getOpponent(tournamentPlayer: TournamentPlayer): TournamentPlayer? {
        return when (tournamentPlayer) {
            tournamentPlayer1.target -> tournamentPlayer2.target
            tournamentPlayer2.target -> tournamentPlayer1.target
            else -> null
        }
    }

    fun getPlayerPoints(tournamentPlayer: TournamentPlayer): Int {
        return when (tournamentPlayer) {
            tournamentPlayer1.target -> player1Points
            tournamentPlayer2.target -> player2Points
            else -> 0
        }
    }
}
