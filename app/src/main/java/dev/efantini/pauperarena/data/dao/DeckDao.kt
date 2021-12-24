package dev.efantini.pauperarena.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import dev.efantini.pauperarena.data.models.Deck
import dev.efantini.pauperarena.data.models.DeckWithCards

@Dao
interface DeckDao {
    @Query("SELECT * FROM deck")
    fun getAll(): List<Deck>

    @Transaction
    @Query("SELECT * FROM deck")
    fun getAllWithCards(): List<DeckWithCards>

    @Query("SELECT * FROM deck WHERE playerOwnerId IN (:playerIds)")
    fun getAllByPlayer(playerIds: LongArray): List<Deck>

    @Query("SELECT * FROM deck WHERE name LIKE :name ")
    fun getAllByExactName(name: String): List<Deck>

    @Query("SELECT * FROM deck WHERE name LIKE '%' || :name || '%' ")
    fun getAllByName(name: String): List<Deck>

    @Insert
    fun insertAll(vararg element: Deck)

    @Delete
    fun delete(element: Deck)
}
