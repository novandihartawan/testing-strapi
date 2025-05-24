package com.example.materiapp

import com.example.materiapp.model.CreateMateriRequest
import com.example.materiapp.model.MateriItem
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.Response

interface ApiService {
    // GET semua materi
    @GET("materis")
    suspend fun getMateris(): List<MateriItem>

    // POST materi baru
    @POST("materis")
    suspend fun createMateri(
        @Body body: CreateMateriRequest
    ): Response<Unit>

    // PUT (update) materi berdasarkan ID
    @PUT("materis/{id}")
    suspend fun updateMateri(
        @Path("id") id: Int,
        @Body body: CreateMateriRequest
    ): Response<Unit>

    // DELETE (hapus) materi
    @DELETE("materis/{id}")
    suspend fun deleteMateri(
        @Path("id") id: Int
    ): Response<Unit>

}


