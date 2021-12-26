package dev.efantini.pauperarena.data.datasources

import dev.efantini.pauperarena.data.ObjectBox
import dev.efantini.pauperarena.data.models.Player
import dev.efantini.pauperarena.data.models.Player_
import io.objectbox.query.OrderFlags
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class PlayerRepository private constructor(
    private val defaultDispatcher: CoroutineDispatcher
) : GenericRepositoryInterface<Player> {

    override suspend fun getItems(): List<Player> {
        return withContext(defaultDispatcher) {
            ObjectBox.get().boxFor(Player::class.java)
                .query()
                .order(Player_.firstName, OrderFlags.DESCENDING)
                .build().find()
        }
    }

    override suspend fun putItems(items: List<Player>) {
        return withContext(defaultDispatcher) {
            ObjectBox.get().boxFor(Player::class.java).put(items)
        }
    }

    override suspend fun deleteItems(items: List<Player>) {
        return withContext(defaultDispatcher) {
            ObjectBox.get().boxFor(Player::class.java).remove(items)
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: PlayerRepository? = null
        fun getInstance(
            defaultDispatcher: CoroutineDispatcher = Dispatchers.IO
        ): PlayerRepository = INSTANCE ?: synchronized(this) {
            INSTANCE ?: PlayerRepository(defaultDispatcher).also { INSTANCE = it }
        }
    }
}
