package edu.ucne.pedrovladimir_p2_ap2.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "entity")
data class Entity(
    @PrimaryKey
    val Id: Int? = null,
)