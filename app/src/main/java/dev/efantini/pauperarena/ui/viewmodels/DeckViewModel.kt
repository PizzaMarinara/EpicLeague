package dev.efantini.pauperarena.ui.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.efantini.pauperarena.data.datasources.DeckRepository
import dev.efantini.pauperarena.data.models.Deck
import dev.efantini.pauperarena.ui.states.DeckItemUiState
import dev.efantini.pauperarena.ui.states.DeckListUiState
import kotlinx.coroutines.launch

class DeckViewModel(private val deckRepository: DeckRepository) : ViewModel() {

    init {
        getDecks()
    }

    var uiState by mutableStateOf(DeckListUiState())
        private set

    fun getDecks() {
        viewModelScope.launch {
            val items = deckRepository.getItems()
            uiState = uiState.copy(deckItems = items.map { DeckItemUiState(deck = it) })
        }
    }

    fun putDecks(items: List<Deck>) {
        viewModelScope.launch {
            deckRepository.putItems(items)
        }
    }

    fun deleteDecks(items: List<Deck>) {
        viewModelScope.launch {
            deckRepository.deleteItems(items)
        }
    }
}
