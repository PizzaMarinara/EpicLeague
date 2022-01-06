package dev.efantini.epicleague.data.datasources

import dev.efantini.epicleague.data.ObjectBox
import dev.efantini.epicleague.data.models.Deck
import dev.efantini.epicleague.data.models.Deck_
import io.objectbox.query.OrderFlags
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DeckRepository private constructor(
    private val defaultDispatcher: CoroutineDispatcher
) : GenericRepositoryInterface<Deck> {

    override suspend fun getItems(): List<Deck> {
        return withContext(defaultDispatcher) {
            ObjectBox.get().boxFor(Deck::class.java)
                .query()
                .order(Deck_.name, OrderFlags.DESCENDING)
                .build().find()
        }
    }

    override suspend fun putItems(items: List<Deck>) {
        return withContext(defaultDispatcher) {
            ObjectBox.get().boxFor(Deck::class.java).put(items)
        }
    }

    override suspend fun deleteItems(items: List<Deck>) {
        return withContext(defaultDispatcher) {
            ObjectBox.get().boxFor(Deck::class.java).remove(items)
        }
    }

    suspend fun getDecksForPlayer(playerId: Long): List<Deck> {
        return withContext(defaultDispatcher) {
            ObjectBox.get().boxFor(Deck::class.java)
                .query(Deck_.playerId.equal(playerId))
                .order(Deck_.name, OrderFlags.DESCENDING)
                .build().find()
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: DeckRepository? = null
        fun getInstance(
            defaultDispatcher: CoroutineDispatcher = Dispatchers.IO
        ): DeckRepository = INSTANCE ?: synchronized(this) {
            INSTANCE ?: DeckRepository(defaultDispatcher).also { INSTANCE = it }
        }
    }
}
