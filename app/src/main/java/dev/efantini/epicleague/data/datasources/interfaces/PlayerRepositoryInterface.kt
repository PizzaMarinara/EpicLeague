package dev.efantini.epicleague.data.datasources.interfaces

import dev.efantini.epicleague.data.models.Player

interface PlayerRepositoryInterface : GenericRepositoryInterface<Player> {
    suspend fun getPlayersNotInTournament(tournamentId: Long): List<Player>
}
