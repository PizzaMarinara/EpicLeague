package dev.efantini.epicleague.data.datasources

import dev.efantini.epicleague.data.ObjectBox
import dev.efantini.epicleague.data.datasources.interfaces.PlayerRepositoryInterface
import dev.efantini.epicleague.data.models.Player
import dev.efantini.epicleague.data.models.Player_
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class PlayerRepository constructor(
    private val defaultDispatcher: CoroutineDispatcher
) : PlayerRepositoryInterface {

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

    override suspend fun getElementById(id: Long): Player? {
        return withContext(defaultDispatcher) {
            ObjectBox.get().boxFor(Player::class.java)
                .query(Player_.id.equal(id))
                .order(Player_.lastName)
                .order(Player_.firstName)
                .build().findFirst()
        }
    }

    override suspend fun getPlayersNotInTournament(tournamentId: Long): List<Player> {
        return withContext(defaultDispatcher) {
            val builder = ObjectBox.get().boxFor(Player::class.java).query()
            builder.filter { player ->
                player.tournamentPlayers.isEmpty() ||
                    player.tournamentPlayers.all { tournamentPlayer ->
                        tournamentPlayer.tournament.target?.id != tournamentId
                    }
            }
            builder.build().find()
        }
    }
}
