package dev.efantini.epicleague.data.datasources

import dev.efantini.epicleague.data.ObjectBox
import dev.efantini.epicleague.data.datasources.interfaces.TournamentPlayerRepositoryInterface
import dev.efantini.epicleague.data.models.TournamentPlayer
import dev.efantini.epicleague.data.models.TournamentPlayer_
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class TournamentPlayerRepository constructor(
    private val defaultDispatcher: CoroutineDispatcher
) : TournamentPlayerRepositoryInterface {

    override suspend fun getItems(): List<TournamentPlayer> {
        return withContext(defaultDispatcher) {
            ObjectBox.get().boxFor(TournamentPlayer::class.java)
                .query()
                .build().find()
        }
    }

    override suspend fun putItems(items: List<TournamentPlayer>) {
        return withContext(defaultDispatcher) {
            ObjectBox.get().boxFor(TournamentPlayer::class.java).put(items)
        }
    }

    override suspend fun deleteItems(items: List<TournamentPlayer>) {
        return withContext(defaultDispatcher) {
            ObjectBox.get().boxFor(TournamentPlayer::class.java).remove(items)
        }
    }

    override suspend fun getElementById(id: Long): TournamentPlayer? {
        return withContext(defaultDispatcher) {
            ObjectBox.get().boxFor(TournamentPlayer::class.java)
                .query(TournamentPlayer_.id.equal(id))
                .build().findFirst()
        }
    }

    override suspend fun getPlayersForTournament(tournamentId: Long): List<TournamentPlayer> {
        return withContext(defaultDispatcher) {
            ObjectBox.get().boxFor(TournamentPlayer::class.java)
                .query(TournamentPlayer_.tournamentId.equal(tournamentId))
                .build().find()
        }
    }
}
