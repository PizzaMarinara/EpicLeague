package dev.efantini.epicleague.data.models.scryfallapi

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ScryfallApiCard(
    val cardObject: String,
    val id: String,
    val oracleID: String,
    val name: String,
    val uri: String,
    val scryfallURI: String,
    val layout: String,
    val highresImage: Boolean,
    val imageStatus: String,
    val imageUris: ImageUris,
    val manaCost: String,
    val cmc: Long,
    val typeLine: String,
    val oracleText: String,
    val colors: List<String>,
    val colorIdentity: List<String>,
    val keywords: List<String>,
    val legalities: Legalities,
    val rulingsURI: String,
    val rarity: String,
    val artist: String,
)

@JsonClass(generateAdapter = true)
data class ImageUris(
    val small: String,
    val normal: String,
    val large: String,
    val png: String,
    val artCrop: String,
    val borderCrop: String
)

@JsonClass(generateAdapter = true)
data class Legalities(
    val standard: String,
    val future: String,
    val historic: String,
    val gladiator: String,
    val pioneer: String,
    val modern: String,
    val legacy: String,
    val pauper: String,
    val vintage: String,
    val penny: String,
    val commander: String,
    val brawl: String,
    val historicbrawl: String,
    val alchemy: String,
    val paupercommander: String,
    val duel: String,
    val oldschool: String,
    val premodern: String
)
