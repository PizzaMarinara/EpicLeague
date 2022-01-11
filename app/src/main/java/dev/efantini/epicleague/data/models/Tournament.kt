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
            turnNumber = tournamentRounds.size + 1
        ).also { tournamentRound ->
            this.tournamentRounds.add(tournamentRound)
        }.also { tournamentRound ->
            pairPlayers()?.let { tournamentRound.tournamentMatches.addAll(it) }
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

    private fun pairPlayers(): MutableList<TournamentMatch>? {
        val roundMatches = mutableListOf<TournamentMatch>()
        val playersToPair = getStandings().toMutableList()
        val playerIterator = playersToPair.iterator()

        while (playerIterator.hasNext()) {
            val tournamentPlayer = playerIterator.next()
            if (roundMatches.none { tournamentMatch ->
                tournamentMatch.isPlayerPlaying(tournamentPlayer)
            }
            ) {
                val possibleOpponents = playersToPair.filter { possibleOpponent ->
                    possibleOpponent.player != tournamentPlayer.player &&
                        !tournamentPlayer.getOpponentsPlayed().contains(possibleOpponent) &&
                        roundMatches.none { tournamentMatch ->
                            tournamentMatch.isPlayerPlaying(possibleOpponent)
                        }
                }
                if (possibleOpponents.isEmpty() && playerIterator.hasNext()) {
                    return null
                }
                roundMatches.add(
                    TournamentMatch().also { tournamentMatch ->
                        tournamentMatch.matchNumber = roundMatches.size + 1
                        tournamentMatch.tournamentPlayer1.target = tournamentPlayer
                        tournamentMatch.tournamentPlayer2.target = possibleOpponents.firstOrNull()
                    }
                )
            }
            playerIterator.remove()
        }
        return roundMatches
    }
}
