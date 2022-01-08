package dev.efantini.epicleague

import dev.efantini.epicleague.data.models.Deck
import dev.efantini.epicleague.data.models.Player
import dev.efantini.epicleague.data.models.Tournament
import dev.efantini.epicleague.data.models.TournamentMatch
import dev.efantini.epicleague.data.models.TournamentPlayer
import dev.efantini.epicleague.data.models.TournamentRound
import org.junit.Before
import org.junit.Test

class MatchTest : AbstractObjectBoxTest() {
    @Before
    override fun setUp() {
        super.setUp()
        val p1 = Player(firstName = "Enrico", lastName = "Fantini")
        val p2 = Player(firstName = "Lorenzo", lastName = "Lanzi")
        val p3 = Player(firstName = "Enrico", lastName = "Casanova")
        val p4 = Player(firstName = "Fabio", lastName = "Romagnoli")
        val p5 = Player(firstName = "Luca", lastName = "Mosconi")
        val p6 = Player(firstName = "Enrico", lastName = "Canducci")

        val playerBox = store.boxFor(Player::class.java)
        playerBox.put(p1)
        playerBox.put(p2)
        playerBox.put(p3)
        playerBox.put(p4)
        playerBox.put(p5)
        playerBox.put(p6)

        val d1 = Deck(name = "MonoU Faeries")
        val d2 = Deck(name = "Affinity")
        val d3 = Deck(name = "UB Faeries")
        val d4 = Deck(name = "Jund Madness")
        val d5 = Deck(name = "MonoU Delver")
        val d6 = Deck(name = "UB Faeries")

        val deckBox = store.boxFor(Deck::class.java)
        deckBox.put(d1)
        deckBox.put(d2)
        deckBox.put(d3)
        deckBox.put(d4)
        deckBox.put(d5)
        deckBox.put(d6)

        val torneo = Tournament(name = "Torne Al Rosso").apply {
            leaguePointsAssigned = "10-8-6-5-2-2-2-2-1"
            pointsToLast = true
        }

        val tp1 = TournamentPlayer().apply {
            tournament.target = torneo
            player.target = p1
            deck.target = d1
        }
        val tp2 = TournamentPlayer().apply {
            tournament.target = torneo
            player.target = p2
            deck.target = d2
        }
        val tp3 = TournamentPlayer().apply {
            tournament.target = torneo
            player.target = p3
            deck.target = d3
        }
        val tp4 = TournamentPlayer().apply {
            tournament.target = torneo
            player.target = p4
            deck.target = d4
        }
        val tp5 = TournamentPlayer().apply {
            tournament.target = torneo
            player.target = p5
            deck.target = d5
        }
        val tp6 = TournamentPlayer().apply {
            tournament.target = torneo
            player.target = p6
            deck.target = d6
        }

        val tournamentPlayerBox = store.boxFor(TournamentPlayer::class.java)
        tournamentPlayerBox.put(tp1)
        tournamentPlayerBox.put(tp2)
        tournamentPlayerBox.put(tp3)
        tournamentPlayerBox.put(tp4)
        tournamentPlayerBox.put(tp5)
        tournamentPlayerBox.put(tp6)

        val tournamentMatchBox = store.boxFor(TournamentMatch::class.java)

        val r1 = TournamentRound()
        r1.apply {
            val m1 = TournamentMatch().apply {
                tournamentPlayer1.target = tp1
                tournamentPlayer2.target = tp2
                tournamentRound.target = r1
                player1Points = 2
                player2Points = 0
            }
            tournamentMatchBox.put(m1)
            tournamentMatches.add(m1)

            val m2 = TournamentMatch().apply {
                tournamentPlayer1.target = tp3
                tournamentPlayer2.target = tp4
                tournamentRound.target = r1
                player1Points = 2
                player2Points = 0
            }
            tournamentMatchBox.put(m2)
            tournamentMatches.add(m2)

            val m3 = TournamentMatch().apply {
                tournamentPlayer1.target = tp5
                tournamentPlayer2.target = tp6
                tournamentRound.target = r1
                player1Points = 2
                player2Points = 0
            }
            tournamentMatchBox.put(m3)
            tournamentMatches.add(m3)

            tournament.target = torneo
        }

        val r2 = TournamentRound()
        r2.apply {
            val m1 = TournamentMatch().apply {
                tournamentPlayer1.target = tp1
                tournamentPlayer2.target = tp3
                tournamentRound.target = r1
                player1Points = 2
                player2Points = 0
            }
            tournamentMatchBox.put(m1)
            tournamentMatches.add(m1)

            val m2 = TournamentMatch().apply {
                tournamentPlayer1.target = tp2
                tournamentPlayer2.target = tp5
                tournamentRound.target = r1
                player1Points = 2
                player2Points = 0
            }
            tournamentMatchBox.put(m2)
            tournamentMatches.add(m2)

            val m3 = TournamentMatch().apply {
                tournamentPlayer1.target = tp4
                tournamentPlayer2.target = tp6
                tournamentRound.target = r1
                player1Points = 2
                player2Points = 0
            }
            tournamentMatchBox.put(m3)
            tournamentMatches.add(m3)

            tournament.target = torneo
        }

        val r3 = TournamentRound()
        r3.apply {
            val m1 = TournamentMatch().apply {
                tournamentPlayer1.target = tp1
                tournamentPlayer2.target = tp4
                tournamentRound.target = r1
                player1Points = 2
                player2Points = 0
            }
            tournamentMatchBox.put(m1)
            tournamentMatches.add(m1)

            val m2 = TournamentMatch().apply {
                tournamentPlayer1.target = tp2
                tournamentPlayer2.target = tp6
                tournamentRound.target = r1
                player1Points = 2
                player2Points = 0
            }
            tournamentMatchBox.put(m2)
            tournamentMatches.add(m2)

            val m3 = TournamentMatch().apply {
                tournamentPlayer1.target = tp3
                tournamentPlayer2.target = tp5
                tournamentRound.target = r1
                player1Points = 2
                player2Points = 0
            }
            tournamentMatchBox.put(m3)
            tournamentMatches.add(m3)

            tournament.target = torneo
        }

        val roundBox = store.boxFor(TournamentRound::class.java)
        roundBox.put(r1)
        roundBox.put(r2)
        roundBox.put(r3)

        torneo.apply {
            tournamentRounds.add(r1)
            tournamentRounds.add(r2)
            tournamentRounds.add(r3)
            tournamentPlayers.add(tp1)
            tournamentPlayers.add(tp2)
            tournamentPlayers.add(tp3)
            tournamentPlayers.add(tp4)
            tournamentPlayers.add(tp5)
            tournamentPlayers.add(tp6)
        }

        val tournamentBox = store.boxFor(Tournament::class.java)
        tournamentBox.put(torneo)

        /*
        1--3-6-9
        2--0-3-6
        3--3-3-6
        4--0-3-3
        5--3-3-3
        6--0-0-0
         */
    }

    @Test
    fun checkOpponent() {

        val tournamentBox = store.boxFor(Tournament::class.java)
        val torneonazzo = tournamentBox.query()
            .build().find()

        assert(torneonazzo.size > 0)

        val torneoAlRosso = torneonazzo[0]
        val matchEnricoLorenzo = torneoAlRosso.tournamentRounds[0].tournamentMatches[0]
        val enrico = torneoAlRosso
            .tournamentPlayers
            .toList()
            .first { it.player.target.lastName == "Fantini" }
        assert(matchEnricoLorenzo.getOpponent(enrico)?.player?.target?.firstName == "Lorenzo")
    }

    @Test
    fun checkNull() {

        val match = TournamentMatch()
        assert(match.tournamentPlayer1.target == null)
    }
}
