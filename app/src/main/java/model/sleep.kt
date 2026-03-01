package com.esqee.nauraazuragm_2417051005.model
import androidx.annotation.DrawableRes

data class Sleep(
    val hari: String,
    val jam: Int,
    val deskripsi: String,
    @DrawableRes val imageRes: Int
)