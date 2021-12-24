package dev.efantini.pauperarena.data.models.scryfallapi

import dev.efantini.pauperarena.data.models.Card

class ScryfallApiCardMapper {
    private fun map(cards: List<ScryfallApiCard>?): List<Card> {
        return cards?.map {
            Card(
                cardId = it.id,
                oracleId = it.oracleID,
                name = it.name,
                image = it.imageUris.normal,
                imageList = it.imageUris.small,
                manaCost = it.manaCost,
                cmc = it.cmc,
                typeLine = it.typeLine,
                oracleText = it.oracleText,
                colors = it.colors.joinToString(separator = "||"),
                colorIdentity = it.colorIdentity.joinToString(separator = "||"),
                keywords = it.keywords.joinToString(separator = "||")
            )
        } ?: listOf()
    }
}
