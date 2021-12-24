package dev.efantini.pauperarena.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import dev.efantini.pauperarena.data.models.DeckCardRef

@Dao
interface DeckCardRefDao {

    @Insert
    fun insertAll(vararg element: DeckCardRef)

    @Delete
    fun delete(element: DeckCardRef)
}
