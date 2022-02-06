package dev.efantini.epicleague.ui.states

data class TournamentListUiState(
    val ongoingTournaments: List<TournamentItemUiState> = listOf(),
    val completedTournaments: List<TournamentItemUiState> = listOf()
)
