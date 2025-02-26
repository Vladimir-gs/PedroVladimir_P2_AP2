package edu.ucne.pedrovladimir_p2_ap2.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import edu.ucne.pedrovladimir_p2_ap2.data.local.entity.Entity
import kotlinx.coroutines.flow.Flow

@Dao
interface TDao {
    @Upsert
    suspend fun save(entity: Entity)

    @Delete
    suspend fun delete(entity: Entity)

    @Query("SELECT * FROM entity")
    fun getAll(): Flow<List<Entity>>

    @Query("""
        SELECT * 
        FROM entity 
        WHERE Id = :id
        LIMIT 1
        """
    )
    suspend fun find(id: Int): Entity?
}