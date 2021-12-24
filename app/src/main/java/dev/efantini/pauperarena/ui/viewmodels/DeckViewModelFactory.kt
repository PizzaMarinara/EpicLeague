package dev.efantini.pauperarena.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dev.efantini.pauperarena.data.datasources.LocalDeckRepository

class DeckViewModelFactory : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DeckViewModel::class.java)) {
            return DeckViewModel(LocalDeckRepository.getInstance()) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
