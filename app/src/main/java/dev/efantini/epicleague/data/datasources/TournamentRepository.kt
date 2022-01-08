package dev.efantini.epicleague.data.datasources

import dev.efantini.epicleague.data.ObjectBox
import dev.efantini.epicleague.data.models.Player
import dev.efantini.epicleague.data.models.Tournament
import dev.efantini.epicleague.data.models.Tournament_
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class TournamentRepository private constructor(
    private val defaultDispatcher: CoroutineDispatcher
) : GenericRepositoryInterface<Tournament> {

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

    suspend fun getPlayersNotInTournament(tournamentId: Long): List<Player> {
        return withContext(defaultDispatcher) {
            val builder = ObjectBox.get().boxFor(Player::class.java).query()
            builder.filter { player ->
                player.tournamentPlayers.isEmpty() ||
                    player.tournamentPlayers.all { tournamentPlayer ->
                        tournamentPlayer.tournament.target.id != tournamentId
                    }
            }
            builder.build().find()
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: TournamentRepository? = null
        fun getInstance(
            defaultDispatcher: CoroutineDispatcher = Dispatchers.IO
        ): TournamentRepository = INSTANCE ?: synchronized(this) {
            INSTANCE ?: TournamentRepository(defaultDispatcher).also { INSTANCE = it }
        }
    }
}
