package edu.ucne.pedrovladimir_p2_ap2.data.repository

import android.util.Log
import edu.ucne.pedrovladimir_p2_ap2.data.remote.RemoteDataSource
import edu.ucne.pedrovladimir_p2_ap2.data.remote.Resource
import edu.ucne.pedrovladimir_p2_ap2.data.remote.dto.DepositoDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import javax.inject.Inject

class ApiRepository @Inject constructor(
    private val dataSource: RemoteDataSource
){
    fun getAllDepositos(): Flow<Resource<List<DepositoDto>>> =
        flow {
            try {
                emit(Resource.Loading())
                val depositos = dataSource.getAllDepositos()
                emit(Resource.Suceess(depositos))
            } catch (e: HttpException) {
                val errorMessage = e.response()?.errorBody()?.string() ?: e.message()
                Log.e("DepositoRepository", "HttpException: $errorMessage")
                emit(Resource.Error("Error de conexion $errorMessage"))
            } catch (e: Exception) {
                Log.e("DepositoRepository", "Exception: ${e.message}")
                emit(Resource.Error("Error: ${e.message}"))
            }
        }

    suspend fun updateDeposito(id: Int, depositoDto: DepositoDto) = dataSource.updateDeposito(id, depositoDto)

    suspend fun getDeposito(id: Int) = dataSource.getDeposito(id)

    suspend fun saveDeposito(depositoDto: DepositoDto) = dataSource.saveDeposito(depositoDto)
}