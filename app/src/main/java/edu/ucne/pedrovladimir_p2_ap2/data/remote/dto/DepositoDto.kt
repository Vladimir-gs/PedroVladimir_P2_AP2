package edu.ucne.pedrovladimir_p2_ap2.data.remote.dto

import java.util.Date

class DepositoDto (
    val idDeposito: Int? = null,
    val fecha: Date? = null,
    val idCuenta: Int? = 0,
    val concepto: String? = "",
    val monto: Double? = 0.0,
)