package dev.efantini.pauperarena.data.datasources

import dev.efantini.pauperarena.data.AppDatabase
import dev.efantini.pauperarena.data.dao.DeckDao
import dev.efantini.pauperarena.data.models.Card
import dev.efantini.pauperarena.data.models.Deck
import dev.efantini.pauperarena.data.models.DeckCardRef
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class LocalDeckRepository private constructor(
    private val defaultDispatcher: CoroutineDispatcher,
    private val deckDao: DeckDao
) : DeckRepository {

    override suspend fun getDecks(): List<Deck> {
        return withContext(defaultDispatcher) {
            deckDao.getAll()
        }
    }

    suspend fun initializeSomeValues() {
        val deck = Deck(deckId = 1, playerOwnerId = 0, name = "UB Faeries")
        val card = Card(cardId = "asd", name = "Atog", oracleId = "123")
        val deckCardRef = DeckCardRef(deck.deckId, card.cardId, false, 4)
        withContext(defaultDispatcher) {
            AppDatabase.get().getDeckDao().deleteAll()
            AppDatabase.get().getDeckDao().insertAll(deck)
            AppDatabase.get().getCardDao().deleteAll()
            AppDatabase.get().getCardDao().insertAll(card)
            AppDatabase.get().getDeckCardRefDao().deleteAll()
            AppDatabase.get().getDeckCardRefDao().insertAll(deckCardRef)
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: LocalDeckRepository? = null
        fun getInstance(
            defaultDispatcher: CoroutineDispatcher = Dispatchers.IO
        ): LocalDeckRepository = INSTANCE ?: synchronized(this) {
            INSTANCE ?: LocalDeckRepository(
                defaultDispatcher,
                AppDatabase.get().getDeckDao()
            ).also { INSTANCE = it }
        }
    }
}
