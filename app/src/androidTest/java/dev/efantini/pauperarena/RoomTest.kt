package dev.efantini.pauperarena

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import dev.efantini.pauperarena.data.AbstractAppDatabase
import dev.efantini.pauperarena.data.dao.CardDao
import dev.efantini.pauperarena.data.dao.DeckCardRefDao
import dev.efantini.pauperarena.data.dao.DeckDao
import dev.efantini.pauperarena.data.models.Card
import dev.efantini.pauperarena.data.models.Deck
import dev.efantini.pauperarena.data.models.DeckCardRef
import java.io.IOException
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class RoomTest {
    private lateinit var deckDao: DeckDao
    private lateinit var cardDao: CardDao
    private lateinit var deckCardRefDao: DeckCardRefDao
    private lateinit var db: AbstractAppDatabase

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, AbstractAppDatabase::class.java
        ).build()
        deckDao = db.getDeckDao()
        cardDao = db.getCardDao()
        deckCardRefDao = db.getDeckCardRefDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun searchDecksIsCaseInsensitive() {
        val deck = Deck(deckId = 0, playerOwnerId = 0, name = "Affinity")
        val deck2 = Deck(deckId = 0, playerOwnerId = 2, name = "UB Faeries")
        val lista = listOf(deck, deck2)
        deckDao.insertAll(*lista.toTypedArray())
        val byName = deckDao.getAllByExactName("affinity")
        assertThat(byName[0].name, equalTo(deck.name))

        val byName2 = deckDao.getAllByName("fae")
        assertThat(byName2[0].name, equalTo(deck2.name))
    }

    @Test
    @Throws(Exception::class)
    fun cardsWithDeckSearch() {
        val deck = Deck(deckId = 1, playerOwnerId = 0, name = "Affinity")
        val card = Card(cardId = "asd", name = "Atog", oracleId = "123")
        val deckCardRef = DeckCardRef(deck.deckId, card.cardId, false, 4)
        deckDao.insertAll(deck)
        cardDao.insertAll(card)
        deckCardRefDao.insertAll(deckCardRef)
        val byName = deckDao.getAllWithCards()

        assert(byName.firstOrNull()?.cards?.firstOrNull { it.name == "Atog" } != null)
    }
}
