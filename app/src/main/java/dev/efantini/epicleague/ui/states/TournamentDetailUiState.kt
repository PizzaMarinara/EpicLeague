package dev.efantini.epicleague.ui.states

import dev.efantini.epicleague.data.models.Player
import dev.efantini.epicleague.data.models.Tournament

data class TournamentDetailUiState(
    val tournament: Tournament = Tournament(),
    val tournamentPlayers: List<TournamentPlayerItemUiState> = listOf(),
    val availablePlayers: List<Player> = listOf()
)
