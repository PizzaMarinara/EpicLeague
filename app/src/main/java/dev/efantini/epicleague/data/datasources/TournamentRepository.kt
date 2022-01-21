package dev.efantini.epicleague.data.datasources

import dev.efantini.epicleague.data.ObjectBox
import dev.efantini.epicleague.data.datasources.interfaces.TournamentRepositoryInterface
import dev.efantini.epicleague.data.models.Tournament
import dev.efantini.epicleague.data.models.Tournament_
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class TournamentRepository constructor(
    private val defaultDispatcher: CoroutineDispatcher
) : TournamentRepositoryInterface {

    override suspend fun getItems(): List<Tournament> {
        return withContext(defaultDispatcher) {
            ObjectBox.get().boxFor(Tournament::class.java)
                .query()
                .build().find()
        }
    }

    override suspend fun putItems(items: List<Tournament>) {
        return withContext(defaultDispatcher) {
            ObjectBox.get().boxFor(Tournament::class.java).put(items)
        }
    }

    override suspend fun deleteItems(items: List<Tournament>) {
        return withContext(defaultDispatcher) {
            ObjectBox.get().boxFor(Tournament::class.java).remove(items)
        }
    }

    override suspend fun getElementById(id: Long): Tournament? {
        return withContext(defaultDispatcher) {
            ObjectBox.get().boxFor(Tournament::class.java)
                .query(Tournament_.id.equal(id))
                .build().findFirst()
        }
    }
}
