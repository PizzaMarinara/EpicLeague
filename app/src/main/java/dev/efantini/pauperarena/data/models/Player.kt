package dev.efantini.pauperarena.data.models

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id

@Entity
data class Player(
    @Id var id: Long = 0,
    val firstName: String = "",
    val lastName: String = "",
    val permissions: String = ""
) {
    val fullName: String
        get() { return if ("$firstName $lastName".isBlank()) "" else "$firstName $lastName" }
}
