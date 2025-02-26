package edu.ucne.pedrovladimir_p2_ap2.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import edu.ucne.pedrovladimir_p2_ap2.data.local.dao.DepositoDao
import edu.ucne.pedrovladimir_p2_ap2.data.local.entity.DepositoEntity

@Database(
    entities = [DepositoEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDataBase : RoomDatabase() {
    abstract fun depositoDao(): DepositoDao

    companion object
}