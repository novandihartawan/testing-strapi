package com.example.materiapp.model

// If Strapi returns additional metadata, you might need:
data class CreateMateriResponse(
    val data: MateriItem,
    val meta: Any? = null  // Strapi sometimes includes metadata
)
// Or if Strapi wraps it differently:
data class StrapiCreateResponse(
    val data: StrapiMateriData
)

data class StrapiMateriData(
    val id: Int,
    val documentId: String,
    val attributes: MateriAttributes
)

data class MateriAttributes(
    val id: Int,
    val documentId: String? = null,
    val judul: String,
    val deskripsi: String,
    val createdAt: String? = null,
    val updatedAt: String? = null,
    val publishedAt: String? = null
)
