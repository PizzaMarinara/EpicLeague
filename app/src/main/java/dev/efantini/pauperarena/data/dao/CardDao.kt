package dev.efantini.pauperarena.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import dev.efantini.pauperarena.data.models.Card

@Dao
interface CardDao {
    @Query("SELECT * FROM card")
    fun getAll(): List<Card>

    @Query("SELECT * FROM card WHERE name LIKE :name ")
    fun getAllByExactName(name: String): List<Card>

    @Query("SELECT * FROM card WHERE name LIKE '%' || :name || '%' ")
    fun getAllByName(name: String): List<Card>

    @Insert
    fun insertAll(vararg element: Card)

    @Delete
    fun delete(element: Card)
}
