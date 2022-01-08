package dev.efantini.epicleague.data.datasources

import dev.efantini.epicleague.data.ObjectBox
import dev.efantini.epicleague.data.models.TournamentRound
import dev.efantini.epicleague.data.models.TournamentRound_
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class TournamentRoundRepository private constructor(
    private val defaultDispatcher: CoroutineDispatcher
) : GenericRepositoryInterface<TournamentRound> {

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

    companion object {
        @Volatile
        private var INSTANCE: TournamentRoundRepository? = null
        fun getInstance(
            defaultDispatcher: CoroutineDispatcher = Dispatchers.IO
        ): TournamentRoundRepository = INSTANCE ?: synchronized(this) {
            INSTANCE ?: TournamentRoundRepository(defaultDispatcher).also { INSTANCE = it }
        }
    }
}
