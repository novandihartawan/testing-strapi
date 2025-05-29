package com.example.materiapp

import com.example.materiapp.model.CreateMateriRequest
import com.example.materiapp.model.CreateMateriResponse
import com.example.materiapp.model.MateriItem
import com.example.materiapp.model.MateriResponse
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
    suspend fun getMateris(): MateriResponse

    // POST materi baru Return CreateMateriResponse instead of Unit
    @POST("materis")
    suspend fun createMateri(
        @Body body: CreateMateriRequest
    ): Response<CreateMateriResponse>

    // PUT (update) materi berdasarkan ID
    @PUT("materis/{documentId}")
    suspend fun updateMateri(
        @Path("documentId") documentId: String,
        @Body body: CreateMateriRequest
    ): Response<Unit>

    // DELETE (hapus) materi
    @DELETE("materis/{documentId}")
    suspend fun deleteMateri(
        @Path("documentId") documentId: String,
    ): Response<Unit>

}


