package dev.efantini.pauperarena.ui.states

import dev.efantini.pauperarena.data.models.Player

data class DeckListUiState(
    val userFilter: Player? = null,
    val deckItems: List<DeckItemUiState> = listOf()
)
