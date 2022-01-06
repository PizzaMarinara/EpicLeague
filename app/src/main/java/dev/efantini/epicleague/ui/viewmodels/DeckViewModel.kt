package dev.efantini.epicleague.ui.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.efantini.epicleague.data.datasources.DeckRepository
import dev.efantini.epicleague.data.models.Deck
import dev.efantini.epicleague.ui.states.DeckItemUiState
import dev.efantini.epicleague.ui.states.DeckListUiState
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DeckViewModel @Inject constructor() : ViewModel() {

    private val deckRepository = DeckRepository.getInstance()

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
