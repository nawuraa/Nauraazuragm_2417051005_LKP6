package com.esqee.nauraazuragm_2417051005

import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.esqee.nauraazuragm_2417051005.data.model.Sleep
import com.esqee.nauraazuragm_2417051005.ui.theme.DetailScreen
import com.esqee.nauraazuragm_2417051005.ui.theme.LoginScreen
import com.esqee.nauraazuragm_2417051005.ui.theme.RegisterScreen
import com.esqee.nauraazuragm_2417051005.ui.theme.SleepScreen
import com.esqee.nauraazuragm_2417051005.ui.theme.SleepTrackerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SleepTrackerTheme {
                val navController = rememberNavController()
                AppNavigation(navController)
            }
        }
    }
}

@Composable
fun AppNavigation(navController: NavHostController) {
    var sleepLogs by remember { mutableStateOf<List<Sleep>>(emptyList()) }

    NavHost(navController = navController, startDestination = "login") {
        composable("login") { LoginScreen(navController) }
        composable("register") { RegisterScreen(navController) }
        composable("home") {
            SleepScreen (navController, sleepLogs, onUpdateLogs = { sleepLogs = it })
        }
        composable("detail/{hari}") { backStackEntry ->
            val hari = backStackEntry.arguments?.getString("hari")
            val decodedHari = Uri.decode(hari)
            val sleep = sleepLogs.find { it.hari == decodedHari }
            sleep?.let { DetailScreen(it, navController) }
        }
    }
}