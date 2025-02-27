package edu.ucne.pedrovladimir_p2_ap2.presentation.navigation

import kotlinx.serialization.Serializable

sealed class Screen {
    @Serializable
    object Home : Screen()

    @Serializable
    data object DepList : Screen()

    @Serializable
    data class Deposito(val DepositoId: Int): Screen()


    @Serializable
    data class DepEdit(val DepositoId: Int): Screen()
}