package dev.efantini.pauperarena

import dev.efantini.pauperarena.data.models.Deck
import dev.efantini.pauperarena.data.models.Player
import org.junit.Test

class PlayerDeckTest : AbstractObjectBoxTest() {

    @Test
    fun checkPersistence() {
        val p1 = Player(firstName = "Enrico", lastName = "Fantini")
        val p2 = Player(firstName = "Lorenzo", lastName = "Lanzi")

        val d1 = Deck(name = "MonoU Faeries")
        val d2 = Deck(name = "Affinity")

        p1.decks.add(d1)
        p2.decks.add(d2)

        val playerBox = store.boxFor(Player::class.java)
        playerBox.put(p1)
        playerBox.put(p2)

        val giocatori = playerBox.query()
            .build().find()

        assert(giocatori.size == 2)
        giocatori.forEach {
            assert(it.decks.size == 1)
        }
    }
}
