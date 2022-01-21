package dev.efantini.epicleague.data

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.efantini.epicleague.data.datasources.DeckRepository
import dev.efantini.epicleague.data.datasources.PlayerRepository
import dev.efantini.epicleague.data.datasources.TournamentMatchRepository
import dev.efantini.epicleague.data.datasources.TournamentPlayerRepository
import dev.efantini.epicleague.data.datasources.TournamentRepository
import dev.efantini.epicleague.data.datasources.TournamentRoundRepository
import javax.inject.Singleton
import kotlinx.coroutines.Dispatchers

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideMoshi(): Moshi =
        Moshi.Builder().addLast(KotlinJsonAdapterFactory())
            .build()

    @Provides
    @Singleton
    fun provideDecks():
        DeckRepository = DeckRepository(Dispatchers.IO)

    @Provides
    @Singleton
    fun providePlayers():
        PlayerRepository = PlayerRepository(Dispatchers.IO)

    @Provides
    @Singleton
    fun provideTournaments():
        TournamentRepository = TournamentRepository(Dispatchers.IO)

    @Provides
    @Singleton
    fun provideTournamentMatches():
        TournamentMatchRepository = TournamentMatchRepository(Dispatchers.IO)

    @Provides
    @Singleton
    fun provideTournamentPlayers():
        TournamentPlayerRepository = TournamentPlayerRepository(Dispatchers.IO)

    @Provides
    @Singleton
    fun provideTournamentRounds():
        TournamentRoundRepository = TournamentRoundRepository(Dispatchers.IO)
}
