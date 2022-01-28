package dev.efantini.epicleague

import dev.efantini.epicleague.data.models.Deck
import dev.efantini.epicleague.data.models.Player
import dev.efantini.epicleague.data.models.Tournament
import dev.efantini.epicleague.data.models.TournamentPlayer
import org.junit.Before
import org.junit.Test

class Pairings32Test {

    lateinit var torneo: Tournament

    @Before
    fun setUp() {
        val p1 = Player(id = 1, firstName = "Player", lastName = "01")
        val p2 = Player(id = 2, firstName = "Player", lastName = "02")
        val p3 = Player(id = 3, firstName = "Player", lastName = "03")
        val p4 = Player(id = 4, firstName = "Player", lastName = "04")
        val p5 = Player(id = 5, firstName = "Player", lastName = "05")
        val p6 = Player(id = 6, firstName = "Player", lastName = "06")
        val p7 = Player(id = 7, firstName = "Player", lastName = "07")
        val p8 = Player(id = 8, firstName = "Player", lastName = "08")
        val p9 = Player(id = 9, firstName = "Player", lastName = "09")
        val p10 = Player(id = 10, firstName = "Player", lastName = "10")
        val p11 = Player(id = 11, firstName = "Player", lastName = "11")
        val p12 = Player(id = 12, firstName = "Player", lastName = "12")
        val p13 = Player(id = 13, firstName = "Player", lastName = "13")
        val p14 = Player(id = 14, firstName = "Player", lastName = "14")
        val p15 = Player(id = 15, firstName = "Player", lastName = "15")
        val p16 = Player(id = 16, firstName = "Player", lastName = "16")
        val p17 = Player(id = 17, firstName = "Player", lastName = "17")
        val p18 = Player(id = 18, firstName = "Player", lastName = "18")
        val p19 = Player(id = 19, firstName = "Player", lastName = "19")
        val p20 = Player(id = 20, firstName = "Player", lastName = "20")
        val p21 = Player(id = 21, firstName = "Player", lastName = "21")
        val p22 = Player(id = 22, firstName = "Player", lastName = "22")
        val p23 = Player(id = 23, firstName = "Player", lastName = "23")
        val p24 = Player(id = 24, firstName = "Player", lastName = "24")
        val p25 = Player(id = 25, firstName = "Player", lastName = "25")
        val p26 = Player(id = 26, firstName = "Player", lastName = "26")
        val p27 = Player(id = 27, firstName = "Player", lastName = "27")
        val p28 = Player(id = 28, firstName = "Player", lastName = "28")
        val p29 = Player(id = 29, firstName = "Player", lastName = "29")
        val p30 = Player(id = 30, firstName = "Player", lastName = "30")
        val p31 = Player(id = 31, firstName = "Player", lastName = "31")
        val p32 = Player(id = 32, firstName = "Player", lastName = "32")
        /*
        val p1 = Player(id = 1, firstName = "Andy", lastName = "Atkinson")
        val p2 = Player(id = 2, firstName = "Bobby", lastName = "Bonanza")
        val p3 = Player(id = 3, firstName = "Candy", lastName = "Connor")
        val p4 = Player(id = 4, firstName = "Desmond", lastName = "Davidson")
        val p5 = Player(id = 5, firstName = "Emily", lastName = "Evans")
        val p6 = Player(id = 6, firstName = "Frank", lastName = "Fogarty")
        val p7 = Player(id = 7, firstName = "Gerald", lastName = "Green")
        val p8 = Player(id = 8, firstName = "Howard", lastName = "Hopkins")
        val p9 = Player(id = 9, firstName = "Ian", lastName = "Indy")
        val p10 = Player(id = 10, firstName = "John", lastName = "Jameson")
        val p11 = Player(id = 11, firstName = "Kate", lastName = "Kenneth")
        val p12 = Player(id = 12, firstName = "Lea", lastName = "Lovecraft")
        val p13 = Player(id = 13, firstName = "Mary", lastName = "Moody")
        val p14 = Player(id = 14, firstName = "Nancy", lastName = "Novel")
        val p15 = Player(id = 15, firstName = "Oliver", lastName = "Olson")
        val p16 = Player(id = 11, firstName = "Peter", lastName = "Porter")
        */

        val d1 = Deck(id = 1, name = "MonoU Faeries")
        val d2 = Deck(id = 2, name = "Affinity")
        val d3 = Deck(id = 3, name = "UB Faeries")
        val d4 = Deck(id = 4, name = "Jund Madness")
        val d5 = Deck(id = 5, name = "MonoU Delver")
        val d6 = Deck(id = 6, name = "UB Faeries")
        val d7 = Deck(id = 7, name = "Walls Combo")
        val d8 = Deck(id = 8, name = "MoggWarts")

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
        val tp7 = TournamentPlayer().apply {
            id = 7
            tournament.target = torneo
            player.target = p7
            deck.target = d7
        }
        val tp8 = TournamentPlayer().apply {
            id = 8
            tournament.target = torneo
            player.target = p8
            deck.target = d8
        }
        val tp9 = TournamentPlayer().apply {
            id = 9
            tournament.target = torneo
            player.target = p9
        }
        val tp10 = TournamentPlayer().apply {
            id = 10
            tournament.target = torneo
            player.target = p10
        }
        val tp11 = TournamentPlayer().apply {
            id = 11
            tournament.target = torneo
            player.target = p11
        }
        val tp12 = TournamentPlayer().apply {
            id = 12
            tournament.target = torneo
            player.target = p12
        }
        val tp13 = TournamentPlayer().apply {
            id = 13
            tournament.target = torneo
            player.target = p13
        }
        val tp14 = TournamentPlayer().apply {
            id = 14
            tournament.target = torneo
            player.target = p14
        }
        val tp15 = TournamentPlayer().apply {
            id = 15
            tournament.target = torneo
            player.target = p15
        }
        val tp16 = TournamentPlayer().apply {
            id = 16
            tournament.target = torneo
            player.target = p16
        }
        val tp17 = TournamentPlayer().apply {
            id = 17
            tournament.target = torneo
            player.target = p17
        }
        val tp18 = TournamentPlayer().apply {
            id = 18
            tournament.target = torneo
            player.target = p18
        }
        val tp19 = TournamentPlayer().apply {
            id = 19
            tournament.target = torneo
            player.target = p19
        }
        val tp20 = TournamentPlayer().apply {
            id = 20
            tournament.target = torneo
            player.target = p20
        }
        val tp21 = TournamentPlayer().apply {
            id = 21
            tournament.target = torneo
            player.target = p21
        }
        val tp22 = TournamentPlayer().apply {
            id = 22
            tournament.target = torneo
            player.target = p22
        }
        val tp23 = TournamentPlayer().apply {
            id = 23
            tournament.target = torneo
            player.target = p23
        }
        val tp24 = TournamentPlayer().apply {
            id = 24
            tournament.target = torneo
            player.target = p24
        }
        val tp25 = TournamentPlayer().apply {
            id = 25
            tournament.target = torneo
            player.target = p25
        }
        val tp26 = TournamentPlayer().apply {
            id = 26
            tournament.target = torneo
            player.target = p26
        }
        val tp27 = TournamentPlayer().apply {
            id = 27
            tournament.target = torneo
            player.target = p27
        }
        val tp28 = TournamentPlayer().apply {
            id = 28
            tournament.target = torneo
            player.target = p28
        }
        val tp29 = TournamentPlayer().apply {
            id = 29
            tournament.target = torneo
            player.target = p29
        }
        val tp30 = TournamentPlayer().apply {
            id = 30
            tournament.target = torneo
            player.target = p30
        }
        val tp31 = TournamentPlayer().apply {
            id = 31
            tournament.target = torneo
            player.target = p31
        }
        val tp32 = TournamentPlayer().apply {
            id = 32
            tournament.target = torneo
            player.target = p32
        }

        torneo.apply {
            tournamentPlayers.add(tp1)
            tournamentPlayers.add(tp2)
            tournamentPlayers.add(tp3)
            tournamentPlayers.add(tp4)
            tournamentPlayers.add(tp5)
            tournamentPlayers.add(tp6)
            tournamentPlayers.add(tp7)
            tournamentPlayers.add(tp8)
            tournamentPlayers.add(tp9)
            tournamentPlayers.add(tp10)
            tournamentPlayers.add(tp11)
            tournamentPlayers.add(tp12)
            tournamentPlayers.add(tp13)
            tournamentPlayers.add(tp14)
            tournamentPlayers.add(tp15)
            tournamentPlayers.add(tp16)
            tournamentPlayers.add(tp17)
            tournamentPlayers.add(tp18)
            tournamentPlayers.add(tp19)
            tournamentPlayers.add(tp20)
            tournamentPlayers.add(tp21)
            tournamentPlayers.add(tp22)
            tournamentPlayers.add(tp23)
            tournamentPlayers.add(tp24)
            tournamentPlayers.add(tp25)
            tournamentPlayers.add(tp26)
            tournamentPlayers.add(tp27)
            tournamentPlayers.add(tp28)
            tournamentPlayers.add(tp29)
            tournamentPlayers.add(tp30)
            tournamentPlayers.add(tp31)
            tournamentPlayers.add(tp32)
        }
    }

    @Test
    fun `rounds are paired correctly`() {
        torneo.pairNewRound()
        torneo.tournamentRounds[0].tournamentMatches.forEach {
            if (it.tournamentPlayer1.targetId < it.tournamentPlayer2.targetId) {
                it.player1Points = 2
                it.player2Points = 0
            } else {
                it.player1Points = 0
                it.player2Points = 2
            }
        }
        torneo.tournamentRounds[0].isFinished = true
        // printStandings()
        torneo.pairNewRound()
        torneo.tournamentRounds[1].tournamentMatches.forEach {
            if (it.tournamentPlayer1.targetId < it.tournamentPlayer2.targetId) {
                it.player1Points = 2
                it.player2Points = 0
            } else {
                it.player1Points = 0
                it.player2Points = 2
            }
        }
        torneo.tournamentRounds[1].isFinished = true
        // printStandings()
        torneo.pairNewRound()
        torneo.tournamentRounds[2].tournamentMatches.forEach {
            if (it.tournamentPlayer1.targetId < it.tournamentPlayer2.targetId) {
                it.player1Points = 2
                it.player2Points = 0
            } else {
                it.player1Points = 0
                it.player2Points = 2
            }
        }
        torneo.tournamentRounds[2].isFinished = true
        // printStandings()
        torneo.pairNewRound()
        torneo.tournamentRounds[3].tournamentMatches.forEach {
            if (it.tournamentPlayer1.targetId < it.tournamentPlayer2.targetId) {
                it.player1Points = 2
                it.player2Points = 0
            } else {
                it.player1Points = 0
                it.player2Points = 2
            }
        }
        torneo.tournamentRounds[3].isFinished = true
        // printStandings()
        torneo.pairNewRound()
        torneo.tournamentRounds[4].tournamentMatches.forEach {
            if (it.tournamentPlayer1.targetId < it.tournamentPlayer2.targetId) {
                it.player1Points = 2
                it.player2Points = 0
            } else {
                it.player1Points = 0
                it.player2Points = 2
            }
        }
        torneo.tournamentRounds[4].isFinished = true
        // printStandings()
        assert(torneo.tournamentRounds.size > 0)
        printStandingsShort()
        assert(
            torneo.tournamentPlayers.map {
                it.getOpponentsPlayed().distinctBy { oppo ->
                    oppo.id
                }.size == torneo.numberOfRounds
            }.all { true }
        )

        printEndMatches()
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
}
