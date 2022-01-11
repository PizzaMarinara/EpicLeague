package dev.efantini.epicleague

import dev.efantini.epicleague.data.models.Deck
import dev.efantini.epicleague.data.models.Player
import dev.efantini.epicleague.data.models.Tournament
import dev.efantini.epicleague.data.models.TournamentPlayer
import org.junit.Before
import org.junit.Test

class PairingsTest {

    lateinit var torneo: Tournament

    @Before
    fun setUp() {
        val p1 = Player(id = 1, firstName = "Andy", lastName = "Atkinson")
        val p2 = Player(id = 2, firstName = "Bobby", lastName = "Bonanza")
        val p3 = Player(id = 3, firstName = "Candy", lastName = "Connor")
        val p4 = Player(id = 4, firstName = "Desmond", lastName = "Davidson")
        val p5 = Player(id = 5, firstName = "Emily", lastName = "Evans")
        val p6 = Player(id = 6, firstName = "Frank", lastName = "Fogarty")

        val d1 = Deck(id = 1, name = "MonoU Faeries")
        val d2 = Deck(id = 2, name = "Affinity")
        val d3 = Deck(id = 3, name = "UB Faeries")
        val d4 = Deck(id = 4, name = "Jund Madness")
        val d5 = Deck(id = 5, name = "MonoU Delver")
        val d6 = Deck(id = 6, name = "UB Faeries")

        torneo = Tournament(name = "Just a test Tournament").apply {
            leaguePointsAssigned = "10-8-6-5-2-2-2-2-1"
            pointsToLast = true
        }

        val tp1 = TournamentPlayer().apply {
            id = 1
            tournament.target = torneo
            player.target = p1
            deck.target = d1
        }
        val tp2 = TournamentPlayer().apply {
            id = 2
            tournament.target = torneo
            player.target = p2
            deck.target = d2
        }
        val tp3 = TournamentPlayer().apply {
            id = 3
            tournament.target = torneo
            player.target = p3
            deck.target = d3
        }
        val tp4 = TournamentPlayer().apply {
            id = 4
            tournament.target = torneo
            player.target = p4
            deck.target = d4
        }
        val tp5 = TournamentPlayer().apply {
            id = 5
            tournament.target = torneo
            player.target = p5
            deck.target = d5
        }
        val tp6 = TournamentPlayer().apply {
            id = 6
            tournament.target = torneo
            player.target = p6
            deck.target = d6
        }

        torneo.apply {
            tournamentPlayers.add(tp1)
            tournamentPlayers.add(tp2)
            tournamentPlayers.add(tp3)
            tournamentPlayers.add(tp4)
            tournamentPlayers.add(tp5)
            tournamentPlayers.add(tp6)
        }
    }

    @Test
    fun `first round is paired correctly`() {
        torneo.pairNewRound()
        assert(torneo.tournamentRounds.size > 0)
        torneo.tournamentRounds.forEach { tournamentRound ->
            assert(tournamentRound.tournamentMatches.size > 0)
            tournamentRound.tournamentMatches.forEach { tournamentMatch ->
                val player1 = tournamentMatch.tournamentPlayer1.target
                    ?.player?.target?.fullName ?: "BYE"
                val player2 = tournamentMatch.tournamentPlayer2.target
                    ?.player?.target?.fullName ?: "BYE"
                println("---")
                println("Round: " + tournamentRound.turnNumber)
                println("Match: " + tournamentMatch.matchNumber)
                println("$player1 VS $player2")
                println("---")
                assert(tournamentMatch.getWinner()?.player?.target?.fullName == null)
            }
        }
    }

    @Test
    fun `first round is paired correctly even with a bye`() {
        torneo.tournamentPlayers.removeLast()
        torneo.pairNewRound()
        assert(torneo.tournamentRounds.size > 0)
        torneo.tournamentRounds.forEach { tournamentRound ->
            assert(tournamentRound.tournamentMatches.size > 0)
            tournamentRound.tournamentMatches.forEach { tournamentMatch ->
                val player1 = tournamentMatch.tournamentPlayer1.target
                    ?.player?.target?.fullName ?: "BYE"
                val player2 = tournamentMatch.tournamentPlayer2.target
                    ?.player?.target?.fullName ?: "BYE"
                println("---")
                println("Round: " + tournamentRound.turnNumber)
                println("Match: " + tournamentMatch.matchNumber)
                println("$player1 VS $player2")
                println("---")
                if (tournamentMatch.matchNumber == 3)
                    assert(tournamentMatch.getWinner()?.player?.target?.fullName == "Emily Evans")
                else
                    assert(tournamentMatch.getWinner()?.player?.target?.fullName == null)
            }
        }
    }

    @Test
    fun `subsequent rounds are paired correctly even with a bye`() {
        // torneo.tournamentPlayers.removeLast()
        torneo.pairNewRound()
        torneo.tournamentRounds.forEach { tournamentRound ->
            tournamentRound.tournamentMatches.forEach { tournamentMatch ->
                if (tournamentMatch.tournamentPlayer1.targetId
                    < tournamentMatch.tournamentPlayer2.targetId
                ) {
                    tournamentMatch.player1Points = 2
                    tournamentMatch.player2Points = 0
                } else {
                    tournamentMatch.player1Points = 0
                    tournamentMatch.player2Points = 2
                }
            }
        }
        torneo.pairNewRound()
        torneo.tournamentRounds.forEach { tournamentRound ->
            tournamentRound.tournamentMatches.forEach { tournamentMatch ->
                if (tournamentMatch.tournamentPlayer1.targetId
                    < tournamentMatch.tournamentPlayer2.targetId
                ) {
                    tournamentMatch.player1Points = 2
                    tournamentMatch.player2Points = 0
                } else {
                    tournamentMatch.player1Points = 0
                    tournamentMatch.player2Points = 2
                }
            }
        }
        torneo.pairNewRound()
        torneo.tournamentRounds.forEach { tournamentRound ->
            tournamentRound.tournamentMatches.forEach { tournamentMatch ->
                if (tournamentMatch.tournamentPlayer1.targetId
                    < tournamentMatch.tournamentPlayer2.targetId
                ) {
                    tournamentMatch.player1Points = 2
                    tournamentMatch.player2Points = 0
                } else {
                    tournamentMatch.player1Points = 0
                    tournamentMatch.player2Points = 2
                }
            }
        }
        assert(torneo.tournamentRounds.size > 0)
        torneo.tournamentRounds.forEach { tournamentRound ->
            tournamentRound.tournamentMatches.forEach { tournamentMatch ->
                val player1 = tournamentMatch.tournamentPlayer1.target
                    ?.player?.target?.fullName ?: "BYE"
                val player2 = tournamentMatch.tournamentPlayer2.target
                    ?.player?.target?.fullName ?: "BYE"
                println("---")
                println("Round: " + tournamentRound.turnNumber)
                println("Match: " + tournamentMatch.matchNumber)
                println("$player1 VS $player2")
                println("---")
            }
        }
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
}
