package edu.ucne.pedrovladimir_p2_ap2.data.remote

import edu.ucne.pedrovladimir_p2_ap2.data.remote.dto.DepositoDto
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val depositoApi: DepositoApi
){
    suspend fun getAllDepositos(): List<DepositoDto> = depositoApi.getAllDepositos()

    suspend fun getDeposito(id: Int): DepositoDto = depositoApi.getDeposito(id)

    suspend fun saveDeposito(deposito: DepositoDto) = depositoApi.saveDeposito(deposito)

    suspend fun updateDeposito(id: Int, deposito: DepositoDto) = depositoApi.updateDeposito(id, deposito)
}