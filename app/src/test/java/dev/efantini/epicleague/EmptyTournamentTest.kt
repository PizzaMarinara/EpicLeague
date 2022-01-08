package dev.efantini.epicleague

import dev.efantini.epicleague.data.models.Deck
import dev.efantini.epicleague.data.models.Player
import dev.efantini.epicleague.data.models.Tournament
import dev.efantini.epicleague.data.models.TournamentPlayer
import org.junit.Before
import org.junit.Test

class EmptyTournamentTest : AbstractObjectBoxTest() {

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

        torneo.apply {
            tournamentPlayers.add(tp1)
            tournamentPlayers.add(tp2)
            tournamentPlayers.add(tp3)
            tournamentPlayers.add(tp4)
            tournamentPlayers.add(tp5)
            tournamentPlayers.add(tp6)
        }

        val tournamentBox = store.boxFor(Tournament::class.java)
        tournamentBox.put(torneo)
    }

    @Test
    fun checkTorneo() {

        val tournamentBox = store.boxFor(Tournament::class.java)
        val torneonazzo = tournamentBox.query()
            .build().find()

        assert(torneonazzo.size > 0)

        val torneoAlRosso = torneonazzo[0]

        assert(torneoAlRosso.tournamentPlayers.size == 6)

        val classifica = torneoAlRosso.getStandings()

        classifica.forEach {
            println("----")
            println(it.player.target.fullName + " - Points:" + it.getTournamentPoints())
            println("----")
        }
        assert(classifica.isNotEmpty())
    }
}
