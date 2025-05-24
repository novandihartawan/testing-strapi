package com.example.materiapp.model

data class CreateMateriRequest(
    val data: MateriInput
)

data class MateriInput(
    val judul: String,
    val deskripsi: String
)