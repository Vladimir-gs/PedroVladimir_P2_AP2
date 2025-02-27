package edu.ucne.pedrovladimir_p2_ap2.data.remote

import edu.ucne.pedrovladimir_p2_ap2.data.remote.dto.DepositoDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface DepositoApi {
    @GET("api/deposito")
    suspend fun getAllDepositos(): List<DepositoDto>

    @GET("api/deposito/{id}")
    suspend fun getDeposito(@Path("id") id: Int): DepositoDto

    @POST("api/deposito")
    suspend fun saveDeposito(@Body ticket: DepositoDto)

    @PUT("api/deposito/{id}")
    suspend fun updateDeposito(
        @Path("id") id: Int,
        @Body deposito: DepositoDto
    ): DepositoDto
}