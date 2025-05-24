package com.example.materiapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import com.example.materiapp.model.CreateMateriRequest
import com.example.materiapp.model.CreateMateriResponse
import com.example.materiapp.model.MateriInput
import com.example.materiapp.model.MateriItem


@Composable
fun MateriListScreen(
    materiList: List<MateriItem>,
    onEdit: (MateriItem) -> Unit
) {
    if (materiList.isEmpty()) {
        Text("Tidak ada data ditemukan", modifier = Modifier.padding(16.dp))
    } else {
        LazyColumn {
            items(materiList) { item ->
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(text = "Judul: ${item.judul}")
                    Text(text = item.deskripsi)
                    Button(onClick = { onEdit(item) }) {
                        Text("Edit")
                    }
                    Divider()
                }
            }
        }
    }
}

@Composable
fun CreateMateriScreen(
    existing: MateriItem? = null,
    onDone: () -> Unit
) {
    var judul by remember { mutableStateOf(existing?.judul ?: "") }
    var deskripsi by remember { mutableStateOf(existing?.deskripsi ?: "") }
    val scope = rememberCoroutineScope()

    Column(modifier = Modifier.padding(16.dp)) {
        OutlinedTextField(value = judul, onValueChange = { judul = it }, label = { Text("Judul") })
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(value = deskripsi, onValueChange = { deskripsi = it }, label = { Text("Deskripsi") })
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = {
            scope.launch {
                val body = CreateMateriRequest(MateriInput(judul, deskripsi))

                if (existing != null) {
                    val idToUpdate = existing.id
                    val response = ApiClient.apiService.updateMateri(idToUpdate, body)
                    if (response.isSuccessful) {
                        println("✅ UPDATE SUCCESS")
                    } else {
                        println("❌ UPDATE FAILED: ${response.code()} - ${response.errorBody()?.string()}")
                    }
                    onDone()
                } else {
                    val response = ApiClient.apiService.createMateri(body)
                    if (response.isSuccessful) {
                        val newId = (response.body() as? CreateMateriResponse)?.data?.id
                        println("✅ CREATED SUCCESSFULLY with ID: $newId")
                    } else {
                        println("❌ CREATE FAILED: ${response.code()} - ${response.errorBody()?.string()}")
                    }
                }

                onDone()
            }
        })

        {
            Text(if (existing != null) "Update" else "Simpan")
        }
    }
}

@Composable
fun App() {
    var materiList by remember { mutableStateOf<List<MateriItem>>(emptyList()) }
    var showForm by remember { mutableStateOf(false) }
    var selectedItem by remember { mutableStateOf<MateriItem?>(null) }
    val scope = rememberCoroutineScope()

    LaunchedEffect(showForm) {
        if (!showForm) {
            scope.launch {
                try {
                    materiList = ApiClient.apiService.getMateris()
                } catch (e: Exception) {
                    println("LOAD ERROR: ${e.localizedMessage}")
                }
            }
        }
    }

    if (showForm) {
        CreateMateriScreen(existing = selectedItem) {
            showForm = false
            selectedItem = null
        }
    } else {
        Column {
            Button(onClick = {
                selectedItem = null
                showForm = true
            }) {
                Text("Tambah Materi")
            }
            MateriListScreen(
                materiList = materiList,
                onEdit = {
                    selectedItem = it
                    showForm = true
                }
            )
        }
    }
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            App()
        }
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
   App()
}