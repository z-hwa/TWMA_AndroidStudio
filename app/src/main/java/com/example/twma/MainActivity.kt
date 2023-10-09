package com.example.twma

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.twma.ui.theme.TWMATheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            TWMATheme {
                Surface(modifier = Modifier) {
                    AppNaviGation()
                }
            }
        }
    }
}

@Composable
fun AppNaviGation() {
    val navController = rememberNavController()

    NavHost(navController = navController,
        startDestination = "WordInput") {

        composable("WordInput") {
            WordInput(goToWordPage = {navController.navigate(route = "WordPage")})  //前往單字倉庫
        }
        composable("WordPage") {
            WordPage(rebackToWordInput = {navController.navigate(route = "WordInput")}) //回到單字輸入頁
        }

    }
}