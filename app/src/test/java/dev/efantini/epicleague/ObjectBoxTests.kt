package dev.efantini.epicleague

import dev.efantini.epicleague.data.models.Deck
import dev.efantini.epicleague.data.models.Player
import dev.efantini.epicleague.data.models.Player_
import dev.efantini.epicleague.data.models.Tournament
import dev.efantini.epicleague.data.models.TournamentMatch
import dev.efantini.epicleague.data.models.TournamentPlayer
import dev.efantini.epicleague.data.models.TournamentRound
import io.objectbox.query.OrderFlags
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
@DelicateCoroutinesApi
class ObjectBoxTests : AbstractObjectBoxTest() {

    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    @Before
    override fun setUp() {
        super.setUp()
        Dispatchers.setMain(mainThreadSurrogate)
    }

    @After
    override fun tearDown() {
        super.tearDown()
        Dispatchers.resetMain()
        mainThreadSurrogate.close()
    }

    @Test
    fun `insert player in boxstore`() {
        runTest {
            val playerBox = store.boxFor(Player::class.java)
            playerBox.put(Player(firstName = "Enrico", lastName = "Fantini"))
            playerBox.put(Player(firstName = "Lorenzo", lastName = "Lanzi"))
            playerBox.put(Player(firstName = "Enrico", lastName = "Casanova"))

            val playerz = playerBox.query()
                .order(Player_.firstName, OrderFlags.DESCENDING)
                .order(Player_.lastName, OrderFlags.DESCENDING)
                .build().find()

            assert(playerz.size == 3)
        }
    }

    @Test
    fun `insert decks for players in boxstore`() {
        val deckBox = store.boxFor(Deck::class.java)
        val playerBox = store.boxFor(Player::class.java)

        val player1 = Player(firstName = "Enrico", lastName = "Fantini")
        val player2 = Player(firstName = "Lorenzo", lastName = "Lanzi")
        val player3 = Player(firstName = "Enrico", lastName = "Casanova")

        val deck1 = Deck(name = "MonoU Delver").apply { player.target = player1 }
        val deck2 = Deck(name = "UB Faeries").apply { player.target = player1 }
        val deck3 = Deck(name = "Affinity").apply { player.target = player2 }
        val deck4 = Deck(name = "Elves").apply { player.target = player2 }
        val deck5 = Deck(name = "UB Faeries").apply { player.target = player3 }

        deckBox.put(deck1, deck2, deck3, deck4, deck5)

        val playerz = playerBox.query()
            .order(Player_.firstName, OrderFlags.DESCENDING)
            .order(Player_.lastName, OrderFlags.DESCENDING)
            .build().find()

        assert(playerz.size == 3)

        val playersWithFaeries = playerz.filter {
            it.decks.any { deck ->
                deck.name.contains("Faeries", ignoreCase = true)
            }
        }

        assert(playersWithFaeries.any { it.fullName.contains("casanova", ignoreCase = true) })
        assert(playersWithFaeries.any { it.fullName.contains("fantini", ignoreCase = true) })
        assert(playersWithFaeries.none { it.fullName.contains("lanzi", ignoreCase = true) })
    }

    @Test
    fun `insert players for decks in boxstore`() {
        val playerBox = store.boxFor(Player::class.java)

        val player1 = Player(firstName = "Enrico", lastName = "Fantini")
        val player2 = Player(firstName = "Lorenzo", lastName = "Lanzi")
        val player3 = Player(firstName = "Enrico", lastName = "Casanova")

        val deck1 = Deck(name = "MonoU Delver")
        val deck2 = Deck(name = "UB Faeries")
        val deck3 = Deck(name = "Affinity")
        val deck4 = Deck(name = "Elves")
        val deck5 = Deck(name = "UB Faeries")

        player1.decks.add(deck1)
        player1.decks.add(deck2)
        player2.decks.add(deck3)
        player2.decks.add(deck4)
        player3.decks.add(deck5)

        playerBox.put(player1, player2, player3)

        val playerz = playerBox.query()
            .order(Player_.firstName, OrderFlags.DESCENDING)
            .order(Player_.lastName, OrderFlags.DESCENDING)
            .build().find()

        assert(playerz.size == 3)

        val playersWithFaeries = playerz.filter {
            it.decks.any { deck ->
                deck.name.contains("Faeries", ignoreCase = true)
            }
        }

        assert(playersWithFaeries.any { it.fullName.contains("casanova", ignoreCase = true) })
        assert(playersWithFaeries.any { it.fullName.contains("fantini", ignoreCase = true) })
        assert(playersWithFaeries.none { it.fullName.contains("lanzi", ignoreCase = true) })
    }

    @Test
    fun `insert tournament with players in boxstore`() {
        val p1 = Player(firstName = "Enrico", lastName = "Fantini")
        val p2 = Player(firstName = "Lorenzo", lastName = "Lanzi")
        val p3 = Player(firstName = "Enrico", lastName = "Casanova")
        val p4 = Player(firstName = "Fabio", lastName = "Romagnoli")
        val p5 = Player(firstName = "Luca", lastName = "Mosconi")
        val p6 = Player(firstName = "Enrico", lastName = "Canducci")

        val d1 = Deck(name = "MonoU Faeries")
        val d2 = Deck(name = "Affinity")
        val d3 = Deck(name = "UB Faeries")
        val d4 = Deck(name = "Jund Madness")
        val d5 = Deck(name = "MonoU Delver")
        val d6 = Deck(name = "UB Faeries")

        val torneo = Tournament(name = "Torneone Al Rosso")

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

        torneo.tournamentPlayers.add(tp1)
        torneo.tournamentPlayers.add(tp2)
        torneo.tournamentPlayers.add(tp3)
        torneo.tournamentPlayers.add(tp4)
        torneo.tournamentPlayers.add(tp5)
        torneo.tournamentPlayers.add(tp6)

        val r1m1 = TournamentMatch().apply {
            tournamentPlayer1.target = tp1
            tournamentPlayer2.target = tp2
        }
        val r1m2 = TournamentMatch().apply {
            tournamentPlayer1.target = tp3
            tournamentPlayer2.target = tp4
        }
        val r1m3 = TournamentMatch().apply {
            tournamentPlayer1.target = tp5
            tournamentPlayer2.target = tp6
        }

        val r1 = TournamentRound().apply {
            tournamentMatches.add(r1m1)
            tournamentMatches.add(r1m2)
            tournamentMatches.add(r1m3)
        }

        val r2m1 = TournamentMatch().apply {
            tournamentPlayer1.target = tp1
            tournamentPlayer2.target = tp3
        }
        val r2m2 = TournamentMatch().apply {
            tournamentPlayer1.target = tp2
            tournamentPlayer2.target = tp5
        }
        val r2m3 = TournamentMatch().apply {
            tournamentPlayer1.target = tp4
            tournamentPlayer2.target = tp6
        }

        val r2 = TournamentRound().apply {
            tournamentMatches.add(r2m1)
            tournamentMatches.add(r2m2)
            tournamentMatches.add(r2m3)
        }

        val r3m1 = TournamentMatch().apply {
            tournamentPlayer1.target = tp1
            tournamentPlayer2.target = tp4
        }
        val r3m2 = TournamentMatch().apply {
            tournamentPlayer1.target = tp2
            tournamentPlayer2.target = tp6
        }
        val r3m3 = TournamentMatch().apply {
            tournamentPlayer1.target = tp3
            tournamentPlayer2.target = tp5
        }

        val r3 = TournamentRound().apply {
            tournamentMatches.add(r3m1)
            tournamentMatches.add(r3m2)
            tournamentMatches.add(r3m3)
        }

        val tournament = Tournament().apply {
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
        tournamentBox.put(tournament)

        val torneonazzo = tournamentBox.query()
            .build().find()

        assert(torneonazzo.size > 0)
    }
}
