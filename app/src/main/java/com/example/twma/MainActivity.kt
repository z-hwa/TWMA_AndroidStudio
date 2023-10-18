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
import androidx.room.Room
import com.example.twma.ui.theme.TWMATheme

//初始化
val db = Room.databaseBuilder(
    appContext,
    AppDatabase::class.java, "database-name"
).build()

val userDao = db.userDao()

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //額外開線程
        Thread() {
            //初始化資料庫
            InitDp()
        }.start()

        setContent {
            TWMATheme {
                Surface(modifier = Modifier) {
                    AppNaviGation()
                }
            }
        }
    }
}

//初始化DP 並載入單字list資訊
fun InitDp() {

    //檢測list非空
    if(userDao.queryWord(wordID = 1) != null) {

        //把單字做載入
        for(word in userDao.queryAll()) {
            if(word.foreignData!=null && word.localData!=null && word.correctRate!=null && word.extraInf!=null
                && word.id!=null) {
                wordList.AddWord(
                    foreignData = word.foreignData,
                    localData = word.localData,
                    extraInf = word.extraInf,
                    correctRate = word.correctRate,
                    wordId = word.id
                )
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
            WordInput(goToWordPage = {navController.navigate(route = "WordPage")},
                wordList = wordList)  //前往單字倉庫
        }
        composable("WordPage") {
            WordPage(rebackToWordInput = {navController.navigate(route = "WordInput")}, wordList = wordList) //回到單字輸入頁
        }

    }
}