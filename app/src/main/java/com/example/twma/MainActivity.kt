package com.example.twma

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
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
                MyApp() //呼叫APP
            }
        }
    }
}

@Composable
fun AppNaviGation() {
    val navController = rememberNavController()
    NavHost(navController = navController,
        startDestination = "WordPage") {

        composable("WordPage") {
            WordPage(navController = navController)
        }
        composable("WordInput") {
            WordInput(navController = navController)
        }
    }
}


@Composable
fun MyApp(modifier: Modifier = Modifier) {
    var shouldShowNewTutorial: Boolean by rememberSaveable { mutableStateOf(true)}  //是否顯示新的開始頁面

    Surface(modifier) {
        if(shouldShowNewTutorial) {
            //首次開啟app顯示介面
            //NewTutorialPage(onContinueClicked = {shouldShowNewTutorial = false})    //傳遞回呼
            WordInput()
        }else{
            WordPage()
        }
    }
}