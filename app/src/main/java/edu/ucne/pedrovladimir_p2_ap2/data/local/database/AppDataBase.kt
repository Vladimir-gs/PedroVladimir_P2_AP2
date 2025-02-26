package edu.ucne.pedrovladimir_p2_ap2.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import edu.ucne.pedrovladimir_p2_ap2.data.local.dao.TDao
import edu.ucne.pedrovladimir_p2_ap2.data.local.entity.Entity

@Database(
    entities = [Entity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDataBase : RoomDatabase() {
    abstract fun tDao(): TDao

    companion object
}