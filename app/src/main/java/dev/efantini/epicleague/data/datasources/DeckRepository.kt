package dev.efantini.epicleague.data.datasources

import dev.efantini.epicleague.data.ObjectBox
import dev.efantini.epicleague.data.datasources.interfaces.DeckRepositoryInterface
import dev.efantini.epicleague.data.models.Deck
import dev.efantini.epicleague.data.models.Deck_
import io.objectbox.query.OrderFlags
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class DeckRepository constructor(
    private val defaultDispatcher: CoroutineDispatcher
) : DeckRepositoryInterface {

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

    override suspend fun getElementById(id: Long): Deck? {
        return withContext(defaultDispatcher) {
            ObjectBox.get().boxFor(Deck::class.java)
                .query(Deck_.id.equal(id))
                .order(Deck_.name, OrderFlags.DESCENDING)
                .build().findFirst()
        }
    }

    override suspend fun getDecksForPlayer(playerId: Long): List<Deck> {
        return withContext(defaultDispatcher) {
            ObjectBox.get().boxFor(Deck::class.java)
                .query(Deck_.playerId.equal(playerId))
                .order(Deck_.name)
                .build().find()
        }
    }
}
