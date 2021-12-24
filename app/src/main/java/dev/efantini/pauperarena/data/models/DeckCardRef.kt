package dev.efantini.pauperarena.data.models

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Junction
import androidx.room.Relation

@Entity(primaryKeys = ["deckId", "cardId"])
data class DeckCardRef(
    val deckId: Long,
    val cardId: String,
    val sideboard: Boolean,
    val count: Int
)

data class DeckWithCards(
    @Embedded val deck: Deck,
    @Relation(
        parentColumn = "deckId",
        entityColumn = "cardId",
        associateBy = Junction(DeckCardRef::class)
    )
    val cards: List<Card>
)

data class CardWithDecks(
    @Embedded val card: Card,
    @Relation(
        parentColumn = "cardId",
        entityColumn = "deckId",
        associateBy = Junction(DeckCardRef::class)
    )
    val decks: List<Deck>
)
