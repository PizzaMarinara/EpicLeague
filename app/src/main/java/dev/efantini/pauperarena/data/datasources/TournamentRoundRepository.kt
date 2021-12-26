package dev.efantini.pauperarena.data.datasources

import dev.efantini.pauperarena.data.ObjectBox
import dev.efantini.pauperarena.data.models.TournamentRound
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
