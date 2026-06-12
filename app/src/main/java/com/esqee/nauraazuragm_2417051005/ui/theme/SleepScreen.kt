package com.esqee.nauraazuragm_2417051005.ui.theme

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.esqee.nauraazuragm_2417051005.R
import com.esqee.nauraazuragm_2417051005.data.model.Sleep
import com.esqee.nauraazuragm_2417051005.data.repository.SleepRepository

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SleepScreen(
    navController: NavController,
    sleepLogs: List<Sleep>,
    onUpdateLogs: (List<Sleep>) -> Unit
) {
    var isLoading by remember { mutableStateOf(true) }
    var isError by remember { mutableStateOf(false) }
    val repository = remember { SleepRepository() }

    var showBottomSheet by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState()

    var inputHari by remember { mutableStateOf("") }
    var inputJamTidur by remember { mutableStateOf("") }
    var inputJamBangun by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        if (sleepLogs.isEmpty()) {
            isLoading = true
            try {
                val data = repository.getSleep()
                if (data.isNotEmpty()) {
                    onUpdateLogs(data)
                    isError = false
                } else {
                    isError = true
                }
                isLoading = false
            } catch (_: Exception) {
                isLoading = false
                isError = true
            }
        } else {
            isLoading = false
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        when {
            isLoading -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) { CircularProgressIndicator() }
            }
            isError && sleepLogs.isEmpty() -> {
                Box(modifier = Modifier.fillMaxSize().padding(32.dp), contentAlignment = Alignment.Center) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("Gagal Memuat Jurnal Tidur", style = MaterialTheme.typography.titleLarge, color = Color.Red)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text("Cek koneksi internet database Gist Anda", color = Color.Gray)
                    }
                }
            }
            else -> {
                LazyColumn(
                    modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background),
                    contentPadding = PaddingValues(20.dp),
                    verticalArrangement = Arrangement.spacedBy(20.dp)
                ) {
                    item {
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                            Column {
                                Text("Sleep Tracker.", style = MaterialTheme.typography.headlineLarge, fontWeight = FontWeight.Bold)
                                Text("Manajemen Kualitas Istirahat Malam Anda", style = MaterialTheme.typography.bodySmall)
                            }
                            Text("Keluar", color = Color.Red, fontWeight = FontWeight.Bold, modifier = Modifier.clickable {
                                navController.navigate("login") { popUpTo(0) { inclusive = true } }
                            })
                        }
                        Spacer(modifier = Modifier.height(20.dp))

                        Button(
                            onClick = { showBottomSheet = true },
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Text("+ Deteksi Siklus Tidur Baru Semalam")
                        }

                        Spacer(modifier = Modifier.height(20.dp))
                        Text("Rekomendasi Siklus Populer", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold)
                        Spacer(modifier = Modifier.height(8.dp))
                        LazyRow(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                            items(sleepLogs.take(3)) { sleep ->
                                SleepRowItem(sleep, navController)
                            }
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                        Text("Daftar Riwayat Tidur Lengkap", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold)
                    }
                    items(sleepLogs) { sleep ->
                        SleepItem(sleep, navController)
                    }
                }
            }
        }

        if (showBottomSheet) {
            ModalBottomSheet(
                onDismissRequest = { showBottomSheet = false },
                sheetState = sheetState
            ) {
                Column(modifier = Modifier.fillMaxWidth().padding(24.dp).navigationBarsPadding()) {
                    Text("Simulasi Sensor Jurnal Tidur", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
                    Text("Sistem akan secara otomatis menghitung durasi dan menganalisis kualitas tidur Anda.", style = MaterialTheme.typography.bodySmall, modifier = Modifier.padding(bottom = 16.dp))

                    OutlinedTextField(value = inputHari, onValueChange = { inputHari = it }, label = { Text("Hari Pemantauan (Contoh: Sabtu)") }, modifier = Modifier.fillMaxWidth())
                    Spacer(modifier = Modifier.height(12.dp))
                    OutlinedTextField(value = inputJamTidur, onValueChange = { inputJamTidur = it }, label = { Text("Jam Mulai Tidur Malam (Format 24 Jam, Contoh: 22)") }, modifier = Modifier.fillMaxWidth())
                    Spacer(modifier = Modifier.height(12.dp))
                    OutlinedTextField(value = inputJamBangun, onValueChange = { inputJamBangun = it }, label = { Text("Jam Bangun Pagi (Format 24 Jam, Contoh: 6)") }, modifier = Modifier.fillMaxWidth())

                    Spacer(modifier = Modifier.height(20.dp))
                    Button(
                        onClick = {
                            val tTidur = inputJamTidur.toIntOrNull() ?: 22
                            val tBangun = inputJamBangun.toIntOrNull() ?: 6

                            val totalJam = if (tBangun >= tTidur) {
                                tBangun - tTidur
                            } else {
                                (24 - tTidur) + tBangun
                            }

                            val hasilKualitas = if (totalJam >= 7) "Sangat Nyenyak" else "Kurang"

                            val hasilPetaGambar = if (totalJam >= 7) {
                                "https://images.unsplash.com/photo-1505693416388-ac5ce068fe85?w=500"
                            } else {
                                "https://images.unsplash.com/photo-1511295742364-92b9345f6852?w=500"
                            }

                            if (inputHari.isNotEmpty()) {
                                val newLog = Sleep(
                                    hari = inputHari,
                                    durasi = "$totalJam Jam",
                                    kualitas = hasilKualitas,
                                    imageUrl = "https://images.unsplash.com/photo-1511295742364-92b9345f6852" // Menggunakan aset internal di model biner
                                )
                                onUpdateLogs(listOf(newLog) + sleepLogs)

                                inputHari = ""
                                inputJamTidur = ""
                                inputJamBangun = ""
                                showBottomSheet = false
                            }
                        },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text("Aktifkan Komputasi Analisis Sistem")
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }
}

@Composable
fun SleepRowItem(sleep: Sleep, navController: NavController) {
    val placeholderRes = if (sleep.kualitas.contains("Nyenyak", ignoreCase = true)) {
        R.drawable.sleep
    } else {
        R.drawable.jam
    }

    Card(
        modifier = Modifier.width(160.dp).clickable { navController.navigate("detail/${Uri.encode(sleep.hari)}") },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column {
            AsyncImage(
                model = sleep.imageUrl,
                contentDescription = sleep.hari,
                placeholder = painterResource(id = placeholderRes),
                error = painterResource(id = placeholderRes),
                contentScale = ContentScale.Crop,
                modifier = Modifier.height(100.dp).fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = sleep.hari,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(horizontal = 8.dp),
                fontWeight = FontWeight.Bold
            )

            Text(
                text = "Durasi: ${sleep.durasi}",
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 8.dp)
            )
        }
    }
}

@Composable
fun SleepItem(sleep: Sleep, navController: NavController) {
    val placeholderRes = if (sleep.kualitas.contains("Nyenyak", ignoreCase = true)) {
        R.drawable.sleep
    } else {
        R.drawable.jam
    }

    Card(
        modifier = Modifier.fillMaxWidth().clickable { navController.navigate("detail/${Uri.encode(sleep.hari)}") },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column {
            Box {
                AsyncImage(
                    model = sleep.imageUrl,
                    contentDescription = sleep.hari,
                    placeholder = painterResource(id = placeholderRes),
                    error = painterResource(id = placeholderRes),
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxWidth().height(180.dp)
                )
            }
            Column(modifier = Modifier.padding(16.dp)) {
                Text(text = "Hari Evaluasi: ${sleep.hari}", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                Text(text = "Durasi Istirahat: ${sleep.durasi} | Kualitas: ${sleep.kualitas}", style = MaterialTheme.typography.bodyMedium)
                Spacer(modifier = Modifier.height(12.dp))
                Button(onClick = { navController.navigate("detail/${Uri.encode(sleep.hari)}") }, modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(16.dp)) {
                    Text("Lihat Analisis Mendalam")
                }
            }
        }
    }
}