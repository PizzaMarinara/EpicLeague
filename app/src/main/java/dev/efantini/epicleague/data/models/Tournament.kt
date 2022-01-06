package dev.efantini.epicleague.data.models

import io.objectbox.annotation.Backlink
import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import io.objectbox.relation.ToMany

@Entity
data class Tournament(
    @Id var id: Long = 0,
    var name: String = "",
    var pointsAssigned: String = "",
    var pointsToLast: Boolean = true,
    var date: String = ""
) {
    @Backlink(to = "tournament")
    lateinit var rounds: ToMany<TournamentRound>
    @Backlink(to = "tournament")
    lateinit var players: ToMany<TournamentPlayer>

    fun convertPointsToString(points: ArrayList<Int>): String {
        return points.joinToString(separator = "-")
    }

    fun convertStringToPoints(string: String): List<Int> {
        return string.split("-").map { it.toInt() }
    }

    fun getStandings(): List<Triple<Player, Int, Int>> {
        val finalStandings: ArrayList<Pair<Player, Int>> = arrayListOf()
        players.forEach { tournamentPlayer ->
            var accumulatedPoints = 0
            rounds.forEach { round ->
                round.matches.forEach { match ->
                    if (match.player1.target == tournamentPlayer ||
                        match.player2.target == tournamentPlayer
                    ) {
                        when (match.getWinner()) {
                            tournamentPlayer -> accumulatedPoints += 3
                            null -> accumulatedPoints += 1
                        }
                    }
                }
            }
            finalStandings.add(Pair(tournamentPlayer.player.target, accumulatedPoints))
        }
        val campaignPoints = convertStringToPoints(pointsAssigned)
        return finalStandings.sortedByDescending { it.second }.map {
            if (campaignPoints.size < finalStandings.indexOf(it)) {
                if (pointsToLast) {
                    Triple(it.first, it.second, campaignPoints[campaignPoints.lastIndex])
                } else {
                    Triple(it.first, it.second, 0)
                }
            } else {
                Triple(it.first, it.second, campaignPoints[finalStandings.indexOf(it)])
            }
        }
    }
}
