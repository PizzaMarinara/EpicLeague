package dev.efantini.pauperarena

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import dev.efantini.pauperarena.data.models.Deck
import dev.efantini.pauperarena.data.models.Player
import dev.efantini.pauperarena.data.models.Player_
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
}
