package dev.efantini.epicleague.ui.states

import dev.efantini.epicleague.data.models.Player

data class PlayerListUiState(
    val userFilter: Player? = null,
    val playerItems: List<PlayerItemUiState> = listOf()
)
