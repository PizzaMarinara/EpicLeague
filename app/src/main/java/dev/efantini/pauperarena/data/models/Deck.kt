package dev.efantini.pauperarena.data.models

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import io.objectbox.relation.ToOne

@Entity
data class Deck(
    @Id var id: Long = 0,
    var name: String = ""
) {
    lateinit var player: ToOne<Player>
}
