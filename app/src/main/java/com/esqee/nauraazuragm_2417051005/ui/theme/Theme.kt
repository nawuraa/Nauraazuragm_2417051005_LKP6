package com.esqee.nauraazuragm_2417051005.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColorScheme = lightColorScheme(
    primary = SageGreen,          // Tombol utama akan berwarna hijau sage
    background = VanillaCream,     // Latar belakang layar akan berwarna krem vanila
    surface = Color.White,         // Permukaan komponen (Card) akan berwarna putih bersih
    onPrimary = Color.White,       // Teks di dalam tombol utama berwarna putih
    onBackground = Color(0xFF333333), // Teks di atas layar berwarna abu-abu gelap
    onSurface = Color(0xFF333333)     // Teks di dalam Card berwarna abu-abu gelap
)

@Composable
fun SleepTrackerTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = LightColorScheme, // Menggunakan skema warna yang sudah kita buat di atas
        typography = Typography,
        content = content
    )
}