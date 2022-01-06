package dev.efantini.epicleague.data.models

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id

@Entity
class Card(
    @Id var id: Long = 0,
    var scryfallId: String,
    var oracleId: String,
    var name: String,
    var image: String? = null,
    var imageList: String? = null,
    var manaCost: String? = null,
    var cmc: Long? = null,
    var typeLine: String? = null,
    var oracleText: String? = null,
    var colors: String? = null,
    var colorIdentity: String? = null,
    var keywords: String? = null
)
