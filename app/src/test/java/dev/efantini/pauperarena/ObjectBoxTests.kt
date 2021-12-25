package dev.efantini.pauperarena

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import dev.efantini.pauperarena.data.models.Deck
import dev.efantini.pauperarena.data.models.Player
import dev.efantini.pauperarena.data.models.Player_
import dev.efantini.pauperarena.data.models.Tournament
import dev.efantini.pauperarena.data.models.TournamentMatch
import dev.efantini.pauperarena.data.models.TournamentPlayer
import dev.efantini.pauperarena.data.models.TournamentRound
import io.objectbox.query.OrderFlags
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestWatcher
import org.junit.runner.Description

@ExperimentalCoroutinesApi
class ObjectBoxTests : AbstractObjectBoxTest() {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var coroutinesTestRule = CoroutineTestRule()

    class CoroutineTestRule(
        val testDispatcher: TestCoroutineDispatcher = TestCoroutineDispatcher()
    ) : TestWatcher() {

        override fun starting(description: Description?) {
            super.starting(description)
            Dispatchers.setMain(testDispatcher)
        }

        override fun finished(description: Description?) {
            super.finished(description)
            Dispatchers.resetMain()
            testDispatcher.cleanupTestCoroutines()
        }
    }

    @Test
    fun `insert player in boxstore`() {
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

        torneo.players.add(tp1)
        torneo.players.add(tp2)
        torneo.players.add(tp3)
        torneo.players.add(tp4)
        torneo.players.add(tp5)
        torneo.players.add(tp6)

        val r1m1 = TournamentMatch().apply {
            player1.target = tp1
            player2.target = tp2
        }
        val r1m2 = TournamentMatch().apply {
            player1.target = tp3
            player2.target = tp4
        }
        val r1m3 = TournamentMatch().apply {
            player1.target = tp5
            player2.target = tp6
        }

        val r1 = TournamentRound().apply {
            matches.add(r1m1)
            matches.add(r1m2)
            matches.add(r1m3)
        }

        val r2m1 = TournamentMatch().apply {
            player1.target = tp1
            player2.target = tp3
        }
        val r2m2 = TournamentMatch().apply {
            player1.target = tp2
            player2.target = tp5
        }
        val r2m3 = TournamentMatch().apply {
            player1.target = tp4
            player2.target = tp6
        }

        val r2 = TournamentRound().apply {
            matches.add(r2m1)
            matches.add(r2m2)
            matches.add(r2m3)
        }

        val r3m1 = TournamentMatch().apply {
            player1.target = tp1
            player2.target = tp4
        }
        val r3m2 = TournamentMatch().apply {
            player1.target = tp2
            player2.target = tp6
        }
        val r3m3 = TournamentMatch().apply {
            player1.target = tp3
            player2.target = tp5
        }

        val r3 = TournamentRound().apply {
            matches.add(r3m1)
            matches.add(r3m2)
            matches.add(r3m3)
        }

        val tournament = Tournament().apply {
            rounds.add(r1)
            rounds.add(r2)
            rounds.add(r3)
            players.add(tp1)
            players.add(tp2)
            players.add(tp3)
            players.add(tp4)
            players.add(tp5)
            players.add(tp6)
        }

        val tournamentBox = store.boxFor(Tournament::class.java)
        tournamentBox.put(tournament)

        val torneonazzo = tournamentBox.query()
            .build().find()

        assert(torneonazzo.size > 0)
    }
}
