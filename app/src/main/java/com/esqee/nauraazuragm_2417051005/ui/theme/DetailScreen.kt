package com.esqee.nauraazuragm_2417051005.ui.theme


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.esqee.nauraazuragm_2417051005.R
import com.esqee.nauraazuragm_2417051005.data.model.Sleep
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun DetailScreen(sleep: Sleep, navController: NavController) {
    var isLoading by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    val placeholderRes = if (sleep.kualitas.contains("Nyenyak", ignoreCase = true)) {
        R.drawable.sleep
    } else {
        R.drawable.jam
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background)) {
            Box {
                AsyncImage(
                    model = sleep.imageUrl,
                    contentDescription = sleep.hari,
                    placeholder = painterResource(id = placeholderRes),
                    error = painterResource(id = placeholderRes),
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxWidth().height(250.dp)
                )
            }
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Hari Pemantauan: ${sleep.hari}", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
                Text("Total Durasi Tidur: ${sleep.durasi}", style = MaterialTheme.typography.bodyLarge)
                Text("Tingkat Kualitas Tidur: ${sleep.kualitas}", color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(10.dp))
                Text(text = "Aplikasi berhasil mengidentifikasi bahwa pada hari ${sleep.hari} Anda beristirahat selama ${sleep.durasi}. Jaga konsistensi jam tidur untuk kesehatan optimal.", style = MaterialTheme.typography.bodyMedium)

                Spacer(modifier = Modifier.height(24.dp))
                Button(onClick = {
                    coroutineScope.launch {
                        isLoading = true
                        delay(2000)
                        snackbarHostState.showSnackbar("Analisis data tidur hari ${sleep.hari} berhasil divalidasi!")
                        isLoading = false
                    }
                }, modifier = Modifier.fillMaxWidth(), enabled = !isLoading, shape = RoundedCornerShape(16.dp)) {
                    if (isLoading) {
                        CircularProgressIndicator(Modifier.size(20.dp), strokeWidth = 2.dp)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Memproses Analisis...")
                    } else {
                        Text("Simpan & Konfirmasi Hasil")
                    }
                }
                Spacer(modifier = Modifier.height(10.dp))
                OutlinedButton(onClick = { navController.popBackStack() }, modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(16.dp)) {
                    Text("Kembali ke Riwayat")
                }
            }
        }
        SnackbarHost(hostState = snackbarHostState, modifier = Modifier.align(Alignment.Center))
    }
}