package dev.efantini.epicleague.data.datasources

import dev.efantini.epicleague.data.ObjectBox
import dev.efantini.epicleague.data.models.Player
import dev.efantini.epicleague.data.models.Player_
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
                .order(Player_.lastName)
                .order(Player_.firstName)
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

    suspend fun getPlayerById(playerId: Long): Player? {
        return withContext(defaultDispatcher) {
            ObjectBox.get().boxFor(Player::class.java)
                .query(Player_.id.equal(playerId))
                .order(Player_.lastName)
                .order(Player_.firstName)
                .build().findFirst()
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
