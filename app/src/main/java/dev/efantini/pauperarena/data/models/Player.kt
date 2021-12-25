package dev.efantini.pauperarena.data.models

import io.objectbox.annotation.Backlink
import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import io.objectbox.relation.ToMany

@Entity
data class Player(
    @Id var id: Long = 0,
    var firstName: String = "",
    var lastName: String = "",
    var permissions: String = ""
) {
    val fullName: String
        get() { return if ("$firstName $lastName".isBlank()) "" else "$firstName $lastName" }

    @Backlink(to = "player")
    lateinit var decks: ToMany<Deck>
}
