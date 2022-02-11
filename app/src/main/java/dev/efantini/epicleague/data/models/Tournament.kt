package dev.efantini.epicleague.data.models

import dev.efantini.epicleague.domain.PairingsUseCase
import dev.efantini.maximumweightedmatching.GraphEdge
import dev.efantini.maximumweightedmatching.MaximumWeightedMatching
import io.objectbox.annotation.Backlink
import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import io.objectbox.relation.ToMany
import kotlin.math.max
import kotlin.math.min

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
    var numberOfRounds: Int? = null,
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
        fun getMaxNumberOfRounds(players: Int): Int =
            PairingsUseCase.getNumberOfRounds(players)
    }

    fun isStarted(): Boolean {
        return tournamentRounds.isNotEmpty()
    }

    fun isEnded(): Boolean {
        val calculatedRounds = if (numberOfRounds == null) {
            getMaxNumberOfRounds(tournamentPlayers.size)
        } else {
            min(numberOfRounds ?: 0, getMaxNumberOfRounds(tournamentPlayers.size))
        }
        return tournamentRounds.size >= calculatedRounds
    }

    fun pairNewRound(): TournamentRound? {

        if (isEnded()) {
            return null
        }

        return TournamentRound(
            turnNumber = tournamentRounds.size + 1
        ).also { tournamentRound ->
            this.tournamentRounds.add(tournamentRound)
        }.apply {
            this.tournamentMatches.addAll(pairPlayers())
        }
    }

    fun getStandings(): List<TournamentPlayer> {

        return tournamentPlayers.sortedWith(
            compareByDescending<TournamentPlayer> { it.getTournamentPoints() }
                .thenByDescending { it.getOpponentsWinPerc() }
                .thenByDescending { it.getGameWinPerc() }
                .thenByDescending { it.getOpponentsGameWinPerc() }
                .thenBy { it.id }
                .thenBy { it.player.target.lastName }
                .thenBy { it.player.target.firstName }
        )
    }

    private fun pairPlayers(): MutableList<TournamentMatch> {
        val roundMatches = mutableListOf<TournamentMatch>()
        val standings = getActivePlayers()
        val edges = getGraphEdges(standings)
        val matching = MaximumWeightedMatching.maxWeightMatching(edges)

        matching.forEachIndexed { index, match ->
            roundMatches.add(
                TournamentMatch().apply {
                    this.matchNumber = index + 1
                    this.tournamentPlayer1.target = standings[match.first.toInt()]
                    // Check for a bye. Usually it's a -1 value
                    if (match.second.toInt() < 0 || match.second.toInt() >= standings.size) {
                        this.tournamentPlayer2.target = null
                    } else {
                        this.tournamentPlayer2.target = standings[match.second.toInt()]
                    }
                }
            )
        }

        return roundMatches
    }

    private fun getActivePlayers(): List<TournamentPlayer> {

        return getStandings().filter { it.isActive }
    }

    private fun weightTwoPlayers(
        highScore: Long,
        player1: TournamentPlayer,
        player2: TournamentPlayer
    ): Long {

        var weight: Long = 0

        if (!player1.getOpponentsPlayed().contains(player2))
            weight += quality(highScore, highScore) + 1

        val best = max(player1.getTournamentPoints(), player2.getTournamentPoints()).toLong()
        val worst = min(player1.getTournamentPoints(), player2.getTournamentPoints()).toLong()
        val spread = best - worst
        val closeness = highScore - spread
        weight += quality(best, closeness)

        return weight
    }

    private fun quality(importance: Long, closeness: Long): Long {
        return (importance + 1) * (closeness + 1)
    }

    private fun getGraphEdges(tournamentPlayers: List<TournamentPlayer>): List<GraphEdge> {

        val edges = mutableListOf<GraphEdge>()

        val highScore = tournamentPlayers.maxOf { tournamentPlayer ->
            tournamentPlayer.getTournamentPoints()
        }
        tournamentPlayers.forEachIndexed { i, tournamentPlayer1 ->
            for (j in i + 1 until tournamentPlayers.size) {
                edges.add(
                    GraphEdge(
                        i.toLong(),
                        j.toLong(),
                        weightTwoPlayers(
                            highScore.toLong(),
                            tournamentPlayer1,
                            tournamentPlayers[j]
                        )
                    )
                )
            }
        }

        return edges
    }
}
