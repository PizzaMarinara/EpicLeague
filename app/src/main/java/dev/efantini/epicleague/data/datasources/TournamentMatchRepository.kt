package dev.efantini.epicleague.data.datasources

import dev.efantini.epicleague.data.ObjectBox
import dev.efantini.epicleague.data.datasources.interfaces.TournamentMatchRepositoryInterface
import dev.efantini.epicleague.data.models.TournamentMatch
import dev.efantini.epicleague.data.models.TournamentMatch_
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class TournamentMatchRepository constructor(
    private val defaultDispatcher: CoroutineDispatcher
) : TournamentMatchRepositoryInterface {

    override suspend fun getItems(): List<TournamentMatch> {
        return withContext(defaultDispatcher) {
            ObjectBox.get().boxFor(TournamentMatch::class.java)
                .query()
                .build().find()
        }
    }

    override suspend fun putItems(items: List<TournamentMatch>) {
        return withContext(defaultDispatcher) {
            ObjectBox.get().boxFor(TournamentMatch::class.java).put(items)
        }
    }

    override suspend fun deleteItems(items: List<TournamentMatch>) {
        return withContext(defaultDispatcher) {
            ObjectBox.get().boxFor(TournamentMatch::class.java).remove(items)
        }
    }

    override suspend fun getElementById(id: Long): TournamentMatch? {
        return withContext(defaultDispatcher) {
            ObjectBox.get().boxFor(TournamentMatch::class.java)
                .query(TournamentMatch_.id.equal(id))
                .build().findFirst()
        }
    }
}
