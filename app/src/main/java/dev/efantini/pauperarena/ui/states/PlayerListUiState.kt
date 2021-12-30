package dev.efantini.pauperarena.ui.states

import dev.efantini.pauperarena.data.models.Player

data class PlayerListUiState(
    val userFilter: Player? = null,
    val playerItems: List<PlayerItemUiState> = listOf()
)
