package edu.ucne.pedrovladimir_p2_ap2.data.repository

import edu.ucne.pedrovladimir_p2_ap2.data.local.dao.DepositoDao
import edu.ucne.pedrovladimir_p2_ap2.data.local.entity.DepositoEntity
import javax.inject.Inject

class DepositoRepository @Inject constructor(
    private val depositoDao: DepositoDao
){
    suspend fun save(deposito: DepositoEntity) = depositoDao.save(deposito)
    suspend fun delete(deposito: DepositoEntity) = depositoDao.delete(deposito)
    fun getAll() = depositoDao.getAll()
    suspend fun find(id: Int) = depositoDao.find(id)
}