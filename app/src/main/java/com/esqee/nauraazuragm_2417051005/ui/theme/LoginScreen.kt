package com.esqee.nauraazuragm_2417051005.ui.theme


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun LoginScreen(navController: NavController) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Box(modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background), contentAlignment = Alignment.Center) {
        Column(modifier = Modifier.fillMaxWidth().padding(28.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            Text("Selamat Datang", style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
            Text("Masuk untuk memantau kualitas tidur Anda", style = MaterialTheme.typography.bodySmall, textAlign = TextAlign.Center, modifier = Modifier.padding(bottom = 24.dp))

            OutlinedTextField(value = email, onValueChange = { email = it }, label = { Text("Email Akun Tidur") }, modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(12.dp))
            Spacer(modifier = Modifier.height(12.dp))
            OutlinedTextField(value = password, onValueChange = { password = it }, label = { Text("Kata Sandi") }, visualTransformation = PasswordVisualTransformation(), modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(12.dp))

            Spacer(modifier = Modifier.height(24.dp))
            Button(onClick = { navController.navigate("home") { popUpTo("login") { inclusive = true } } }, modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(12.dp)) {
                Text("Mulai Pantau Tidur (Masuk)")
            }
            TextButton(onClick = { navController.navigate("register") }) {
                Text("Belum punya akun pemantau? Daftar")
            }
        }
    }
}

@Composable
fun RegisterScreen(navController: NavController) {
    var nama by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }

    Box(modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background), contentAlignment = Alignment.Center) {
        Column(modifier = Modifier.fillMaxWidth().padding(28.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            Text("Bangun Akun Baru", style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
            Text("Mulai kelola jam tidur yang lebih sehat", style = MaterialTheme.typography.bodySmall, textAlign = TextAlign.Center, modifier = Modifier.padding(bottom = 24.dp))

            OutlinedTextField(value = nama, onValueChange = { nama = it }, label = { Text("Nama Lengkap") }, modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(12.dp))
            Spacer(modifier = Modifier.height(12.dp))
            OutlinedTextField(value = email, onValueChange = { email = it }, label = { Text("Alamat Email Baru") }, modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(12.dp))

            Spacer(modifier = Modifier.height(24.dp))
            Button(onClick = { navController.popBackStack() }, modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(12.dp)) {
                Text("Buat Akun Pemantau")
            }
            TextButton(onClick = { navController.popBackStack() }) {
                Text("Sudah punya akun? Masuk Kembali")
            }
        }
    }
}