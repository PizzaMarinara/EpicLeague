package dev.efantini.pauperarena.data.datasources

import dev.efantini.pauperarena.data.ObjectBox
import dev.efantini.pauperarena.data.models.Deck
import dev.efantini.pauperarena.data.models.Deck_
import io.objectbox.query.OrderFlags
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class LocalDeckRepository private constructor(
    private val defaultDispatcher: CoroutineDispatcher
) : DeckRepository {

    override suspend fun getDecks(): List<Deck> {
        return withContext(defaultDispatcher) {
            ObjectBox.get().boxFor(Deck::class.java)
                .query()
                .order(Deck_.name, OrderFlags.DESCENDING)
                .build().find()
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: LocalDeckRepository? = null
        fun getInstance(
            defaultDispatcher: CoroutineDispatcher = Dispatchers.IO
        ): LocalDeckRepository = INSTANCE ?: synchronized(this) {
            INSTANCE ?: LocalDeckRepository(defaultDispatcher).also { INSTANCE = it }
        }
    }
}
