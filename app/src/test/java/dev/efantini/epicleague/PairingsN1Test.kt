package dev.efantini.epicleague

import dev.efantini.epicleague.data.models.Player
import dev.efantini.epicleague.data.models.Tournament
import dev.efantini.epicleague.data.models.TournamentPlayer
import dev.efantini.epicleague.domain.PairingsUseCase
import kotlin.math.sqrt
import org.junit.Before
import org.junit.Test

class PairingsN1Test {

    lateinit var torneo: Tournament

    private val PLAYER_NUMBER = 16

    @Before
    fun setUp() {

        torneo = Tournament(name = "Just a test Tournament").apply {
            leaguePointsAssigned = "10-8-6-5-2-2-2-2-1"
            pointsToLast = true
        }

        torneo.also {
            for (k in 1..PLAYER_NUMBER) {
                it.tournamentPlayers.add(
                    TournamentPlayer().apply {
                        id = k.toLong()
                        tournament.target = torneo
                        player.target = Player(
                            id = k.toLong(),
                            firstName = "Player",
                            lastName = k.toString()
                        )
                    }
                )
            }
        }
    }

    @Test
    fun `rounds are paired correctly`() {
        while (!torneo.isEnded()) {
            torneo.pairNewRound()
            torneo.tournamentRounds.last().tournamentMatches.forEach {
                if (it.tournamentPlayer1.targetId < it.tournamentPlayer2.targetId) {
                    it.player1Points = 2
                    it.player2Points = 0
                } else {
                    it.player1Points = 0
                    it.player2Points = 2
                }
            }
            torneo.tournamentRounds.last().isFinished = true
        }
        printStandingsShort()
        assert(
            torneo.tournamentPlayers.map {
                it.getOpponentsPlayed().distinctBy { oppo ->
                    oppo.id
                }.size == PairingsUseCase.getNumberOfRounds(PLAYER_NUMBER)
            }.all { true }
        )

        printChampionshipPoints()
    }

    private fun printStandings() {
        torneo.getStandings().forEach {
            println("----")
            println(it.player.target.fullName)
            println("Points:" + it.getTournamentPoints())
            println("OMW:" + it.getOpponentsWinPerc())
            println("GW:" + it.getGameWinPerc())
            println("OGW:" + it.getOpponentsGameWinPerc())
            println("----")
        }
    }

    private fun printStandingsShort() {
        println("----")
        torneo.getStandings().forEach {
            println(
                it.player.target.fullName +
                    " " + it.getTournamentPoints() +
                    " " + it.getOpponentsWinPerc() +
                    " " + it.getGameWinPerc() +
                    " " + it.getOpponentsGameWinPerc()
            )
        }
    }

    private fun printEndMatches() {
        torneo.tournamentRounds.forEach { tournamentRound ->
            assert(tournamentRound.tournamentMatches.size > 0)
            tournamentRound.tournamentMatches.forEach { tournamentMatch ->
                val player1 = tournamentMatch.tournamentPlayer1.target
                    ?.player?.target?.fullName ?: "BYE"
                val player2 = tournamentMatch.tournamentPlayer2.target
                    ?.player?.target?.fullName ?: "BYE"
                val player1P = tournamentMatch.player1Points
                val player2P = tournamentMatch.player2Points
                println("---")
                println("Round: " + tournamentRound.turnNumber)
                println("Match: " + tournamentMatch.matchNumber)
                println("$player1 ($player1P) VS $player2 ($player2P)")
                println("---")
            }
        }
    }

    private fun printChampionshipPoints() {
        println("----")
        calculateIncenterForRankings(torneo.getStandings()).forEach {
            println(
                it.first.player.target.fullName +
                    " " + it.second
            )
        }
    }

    private fun <T> calculateIncenterForRankings(elements: List<T>): List<Pair<T, Double>> {
        val listRankings = arrayListOf<Pair<T, Double>>()
        elements.forEach {
            listRankings.add(Pair(it, 0.0))
        }
        for (k in elements.size - 1 downTo 0) {
            val pairElement = when (elements.size - k) {
                1 -> Pair(elements[k], 1.0)
                2 -> Pair(elements[k], sqrt(2.0) + 1)
                3 -> Pair(elements[k], (sqrt(2.0) + 1) * (sqrt(3.0) + 2) - (sqrt(3.0) + 1))
                else -> Pair(
                    elements[k],
                    (
                        (3 * listRankings[k + 1].second) -
                            (3 * listRankings[k + 2].second) +
                            listRankings[k + 3].second
                        )
                )
            }
            listRankings[k] = pairElement
        }
        return listRankings
    }

    fun getIncenterPoints(position: Int, totalPlayers: Int): Double {
        if (totalPlayers < 2 || position > totalPlayers || position < 0) {
            return 0.0
        }
        return when (totalPlayers - position) {
            0 -> 1.0
            1 -> sqrt(2.0) + 1
            2 -> (sqrt(2.0) + 1) * (sqrt(3.0) + 2) - (sqrt(3.0) + 1)
            else -> (
                (3 * getIncenterPoints(position + 1, totalPlayers)) -
                    (3 * getIncenterPoints(position + 2, totalPlayers)) +
                    getIncenterPoints(position + 3, totalPlayers)
                )
        }
    }
}
