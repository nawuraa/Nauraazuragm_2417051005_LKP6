package com.esqee.nauraazuragm_2417051005

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.esqee.nauraazuragm_2417051005.model.sleepsource
import com.esqee.nauraazuragm_2417051005.ui.theme.Nauraazuragm_2417051005Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Nauraazuragm_2417051005Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(modifier: Modifier = Modifier) {

    val sleep = sleepsource.dummysleep[0]

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {
        Text(text = "Hari: ${sleep.hari}")
        Text(text = "Jam: ${sleep.jam}")
        Text(text = "Deskripsi: ${sleep.deskripsi}")
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Nauraazuragm_2417051005Theme {
        Greeting()
    }
}