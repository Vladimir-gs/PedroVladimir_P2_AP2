package edu.ucne.pedrovladimir_p2_ap2.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "deposito")
data class DepositoEntity(
    @PrimaryKey
    val idDeposito: Int? = null,
    val fecha: String? = null,
    val idCuenta: Int? = null,
    val concepto: String? = null,
    val monto: Double? = null
)