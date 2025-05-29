package com.example.materiapp.model

import com.google.gson.JsonElement

//import com.google.gson.JsonElement
//import com.google.gson.JsonObject
//import com.google.gson.JsonArray

data class MateriResponse(
    val data: List<MateriItem>
)

data class MateriItem(
    val documentId: String,
    val id: Int,
    val judul: String,
    val deskripsi: String   // ❗️Jangan nullable
)

data class MaterAttributes(
    val judul: String,
    val deskripsi: String
)


fun parseDeskripsiToText(deskripsi: JsonElement?): String {
    if (deskripsi == null || !deskripsi.isJsonArray) return "Tidak ada deskripsi"

    val paragraphs = mutableListOf<String>()
    val blocks = deskripsi.asJsonArray

    for (block in blocks) {
        val children = block.asJsonObject["children"]?.asJsonArray ?: continue

        val texts = children.mapNotNull { child ->
            child.asJsonObject["text"]?.asString
        }

        val paragraphText = texts.joinToString("")
        paragraphs.add(paragraphText)
    }

    return paragraphs.joinToString("\n")
}