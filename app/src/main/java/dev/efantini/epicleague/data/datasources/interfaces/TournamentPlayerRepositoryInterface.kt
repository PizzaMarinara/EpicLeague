package dev.efantini.epicleague.data.datasources.interfaces

import dev.efantini.epicleague.data.models.TournamentPlayer

interface TournamentPlayerRepositoryInterface : GenericRepositoryInterface<TournamentPlayer> {
    suspend fun getPlayersForTournament(tournamentId: Long): List<TournamentPlayer>
}
