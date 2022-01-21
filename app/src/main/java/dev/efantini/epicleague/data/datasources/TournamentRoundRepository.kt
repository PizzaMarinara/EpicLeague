package dev.efantini.epicleague.data.datasources

import dev.efantini.epicleague.data.ObjectBox
import dev.efantini.epicleague.data.datasources.interfaces.TournamentRoundRepositoryInterface
import dev.efantini.epicleague.data.models.TournamentRound
import dev.efantini.epicleague.data.models.TournamentRound_
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class TournamentRoundRepository constructor(
    private val defaultDispatcher: CoroutineDispatcher
) : TournamentRoundRepositoryInterface {

    override suspend fun getItems(): List<TournamentRound> {
        return withContext(defaultDispatcher) {
            ObjectBox.get().boxFor(TournamentRound::class.java)
                .query()
                .build().find()
        }
    }

    override suspend fun putItems(items: List<TournamentRound>) {
        return withContext(defaultDispatcher) {
            ObjectBox.get().boxFor(TournamentRound::class.java).put(items)
        }
    }

    override suspend fun deleteItems(items: List<TournamentRound>) {
        return withContext(defaultDispatcher) {
            ObjectBox.get().boxFor(TournamentRound::class.java).remove(items)
        }
    }

    override suspend fun getElementById(id: Long): TournamentRound? {
        return withContext(defaultDispatcher) {
            ObjectBox.get().boxFor(TournamentRound::class.java)
                .query(TournamentRound_.id.equal(id))
                .build().findFirst()
        }
    }
}
