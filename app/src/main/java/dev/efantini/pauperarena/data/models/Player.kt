package dev.efantini.pauperarena.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Player(
    @PrimaryKey(autoGenerate = true) val playerId: Long,
    val firstName: String = "",
    val lastName: String = "",
    val permissions: String = ""
) {
    val fullName: String
        get() { return if ("$firstName $lastName".isBlank()) "" else "$firstName $lastName" }
}
