package dev.efantini.epicleague.data.models

import io.objectbox.annotation.Backlink
import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import io.objectbox.relation.ToMany

@Entity
data class Tournament(
    @Id var id: Long = 0,
    var name: String = "",
    var leaguePointsAssigned: String = "",
    var pointsToLast: Boolean = true,
    var date: String = "",
    var winPoints: Int = 3,
    var drawPoints: Int = 1,
    var losePoints: Int = 0,
    var floorWinPercentage: Double = 0.33
) {
    @Backlink(to = "tournament")
    lateinit var tournamentRounds: ToMany<TournamentRound>
    @Backlink(to = "tournament")
    lateinit var tournamentPlayers: ToMany<TournamentPlayer>

    companion object {
        fun convertPointsToString(points: MutableList<Int>): String {
            return points.joinToString(separator = "-")
        }
        fun convertStringToPoints(string: String): List<Int> {
            return string.split("-").map {
                if (it.isNotEmpty()) {
                    it.toInt()
                } else {
                    0
                }
            }
        }
        fun getNumberOfRounds(players: Int): Int? = when (players) {
            in 2..4 -> 2
            in 5..8 -> 3
            in 9..16 -> 4
            in 17..32 -> 5
            in 33..64 -> 6
            in 65..128 -> 7
            in 129..212 -> 8
            in 213..385 -> 9
            else -> null
        }
    }

    fun pairNewRound(): TournamentRound? {
        val calculatedRounds = getNumberOfRounds(tournamentPlayers.size)
        if (calculatedRounds == null || calculatedRounds <= tournamentRounds.size) {
            return null
        }

        return TournamentRound(
            turnNumber = if (tournamentRounds.isEmpty())
                1
            else
                tournamentRounds.minOf { it.turnNumber } + 1
        ).also { tournamentRound ->
            tournamentRound.tournament.target = this
        }.also { tournamentRound ->
            val roundMatches = mutableListOf<TournamentMatch>()
            getStandings().chunked(2).forEach { pairOfPlayers ->
                roundMatches.add(
                    TournamentMatch().also { tournamentMatch ->
                        tournamentMatch.tournamentRound.target = tournamentRound
                        tournamentMatch.tournamentPlayer1.target = pairOfPlayers[0]
                        if (pairOfPlayers.size > 1)
                            tournamentMatch.tournamentPlayer2.target = pairOfPlayers[1]
                    }
                )
            }
        }
    }

    fun getStandings(): List<TournamentPlayer> {

        return tournamentPlayers.sortedWith(
            compareByDescending<TournamentPlayer> { it.getTournamentPoints() }
                .thenByDescending { it.getOpponentsWinPerc() }
                .thenByDescending { it.getGameWinPerc() }
                .thenByDescending { it.getOpponentsGameWinPerc() }
                .thenBy { it.player.target.lastName }
                .thenBy { it.player.target.firstName }
        )
    }
}
