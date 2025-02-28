package edu.ucne.pedrovladimir_p2_ap2.data.remote

import edu.ucne.pedrovladimir_p2_ap2.data.remote.dto.DepositoDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface DepositoApi {
    @Headers("X-API-Key:test")
    @GET("api/Depositos")
    suspend fun getAllDepositos(): List<DepositoDto>

    @Headers("X-API-Key:test")
    @GET("api/Depositos/{id}")
    suspend fun getDeposito(@Path("id") id: Int): DepositoDto

    @Headers("X-API-Key:test")
    @POST("api/Depositos")
    suspend fun saveDeposito(@Body ticket: DepositoDto)

    @Headers("X-API-Key:test")
    @PUT("api/Depositos/{id}")
    suspend fun updateDeposito(
        @Path("id") id: Int,
        @Body deposito: DepositoDto
    ): DepositoDto
}