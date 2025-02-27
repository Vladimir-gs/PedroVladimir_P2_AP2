package edu.ucne.pedrovladimir_p2_ap2.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import edu.ucne.pedrovladimir_p2_ap2.data.local.database.AppDataBase
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AppModule {
    @Provides
    @Singleton
    fun ProvideContex(@ApplicationContext AppContex: Context) =
        Room.databaseBuilder(
            AppContex,
            AppDataBase::class.java,
            "App.db"
        ).fallbackToDestructiveMigration()
            .build()
    @Provides
    @Singleton
    fun ProvideTDao(appDataBase: AppDataBase) = appDataBase.depositoDao()
}