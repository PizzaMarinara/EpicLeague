package dev.efantini.epicleague

import dev.efantini.epicleague.data.models.Player
import dev.efantini.epicleague.data.models.Tournament
import dev.efantini.epicleague.data.models.TournamentPlayer
import dev.efantini.epicleague.domain.PairingsUseCase
import kotlin.math.sqrt
import org.junit.Before
import org.junit.Test

class PairingsN2Test {

    lateinit var torneo: Tournament
    lateinit var torneo2: Tournament

    private val PLAYER_NUMBER = 16

    @Before
    fun setUp() {

        torneo = Tournament(name = "Just a test Tournament").apply {
            leaguePointsAssigned = "10-8-6-5-2-2-2-2-1"
            pointsToLast = true
        }

        torneo2 = Tournament(name = "Just a test Tournament2").apply {
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

        torneo2.also {
            for (k in 1..PLAYER_NUMBER) {
                it.tournamentPlayers.add(
                    TournamentPlayer().apply {
                        id = k.toLong()
                        tournament.target = torneo2
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
        while (!torneo2.isEnded()) {
            torneo2.pairNewRound()
            torneo2.tournamentRounds.last().tournamentMatches.forEach {
                if (it.tournamentPlayer1.targetId < it.tournamentPlayer2.targetId) {
                    it.player1Points = 0
                    it.player2Points = 2
                } else {
                    it.player1Points = 2
                    it.player2Points = 0
                }
            }
            torneo2.tournamentRounds.last().isFinished = true
        }
        assert(
            torneo.tournamentPlayers.map {
                it.getOpponentsPlayed().distinctBy { oppo ->
                    oppo.id
                }.size == PairingsUseCase.getNumberOfRounds(PLAYER_NUMBER)
            }.all { true }
        )

        printStandingsShort(torneo)
        printStandingsShort(torneo2)
        printChampionshipPoints()
    }

    private fun printStandings(tournament: Tournament) {
        tournament.getStandings().forEach {
            println("----")
            println(it.player.target.fullName)
            println("Points:" + it.getTournamentPoints())
            println("OMW:" + it.getOpponentsWinPerc())
            println("GW:" + it.getGameWinPerc())
            println("OGW:" + it.getOpponentsGameWinPerc())
            println("----")
        }
    }

    private fun printStandingsShort(tournament: Tournament) {
        println("----")
        tournament.getStandings().forEach {
            println(
                it.player.target.fullName +
                    " " + it.getTournamentPoints() +
                    " " + it.getOpponentsWinPerc() +
                    " " + it.getGameWinPerc() +
                    " " + it.getOpponentsGameWinPerc()
            )
        }
    }

    private fun printEndMatches(tournament: Tournament) {
        tournament.tournamentRounds.forEach { tournamentRound ->
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
        val incenterRankings1 = calculateIncenterForRankings(torneo.getStandings())
        val incenterRankings2 = calculateIncenterForRankings(torneo2.getStandings())

        val result = mutableListOf<Pair<TournamentPlayer, Double>>()

        incenterRankings1.forEachIndexed { index, i1 ->
            val secondT = incenterRankings2.find { i2 ->
                i2.first.id == i1.first.id
            }
            result.add(Pair(i1.first, i1.second + (secondT?.second ?: 0.0)))
        }
        result.sortByDescending {
            it.second
        }
        result.forEach {

            val firstResult = incenterRankings1
                .indexOfFirst { i1 -> it.first.id == i1.first.id } + 1
            val secondResult = incenterRankings2
                .indexOfFirst { i2 -> it.first.id == i2.first.id } + 1

            println(
                it.first.player.target.fullName +
                    " " + it.second + " T1:" + firstResult + "° T2:" + secondResult + "°"
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
}
