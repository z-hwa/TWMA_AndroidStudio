package com.example.twma

import android.content.res.Configuration
import android.content.res.Resources
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.twma.ui.theme.TWMATheme

//dp px之間的轉換
/*
fun Int.PxtoDp(): Int = (this / Resources.getSystem().displayMetrics.density).toInt()
fun Int.DptoPx(): Int = (this * Resources.getSystem().displayMetrics.density).toInt()
*/
fun Int.fromPxtoSp(): Int = (this * Resources.getSystem().displayMetrics.scaledDensity).toInt()

//輸入的提示
@Composable
fun InputHint(modifier: Modifier, isDown: Boolean, heightOfHint: Int,
              initHeight: (Int)->Unit,
              lowerHeight: ()->Unit,
              ) {
    Column (modifier = modifier){
        //是否準備繪製
        var readyToDraw by remember {
            mutableStateOf(false)
        }

        //請輸入的column
        Text(text = stringResource(id = R.string.inputData),
            textAlign = TextAlign.Start,
            fontFamily = FontFamily.Cursive,
            modifier = Modifier
                .fillMaxSize()
                .onGloballyPositioned {
                    if (!isDown) initHeight(it.size.height.fromPxtoSp())
                }
                .drawWithContent {
                    if (readyToDraw) drawContent()
                },
            fontSize = heightOfHint.sp,
            onTextLayout = {
                    textLayoutResult ->
                if(textLayoutResult.didOverflowHeight) { lowerHeight() }
                else {readyToDraw = true}
            }
        )
    }
}

//外語資料的欄位
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ForeignData(modifier: Modifier) {
    Column (modifier = modifier){
        //輸入外語資料

        //外語資料暫存
        var foreignData by remember {
            mutableStateOf("")
        }

        TextField(value = foreignData,
            onValueChange = {foreignData = it},
            modifier = Modifier
                .fillMaxSize()
                .alpha(0.75f),
            shape = RoundedCornerShape(11),
            placeholder = { Text(text = stringResource(id = R.string.foreignData))},
            maxLines = 3
        )
    }
}

//本地資料的欄位
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LocalData(modifier: Modifier) {
    Column (modifier = modifier){
        //輸入本地資料
        //外語資料暫存
        var localData by remember {
            mutableStateOf("")
        }

        TextField(value = localData,
            onValueChange = {localData = it},
            modifier = Modifier
                .fillMaxSize()
                .alpha(0.75f),
            shape = RoundedCornerShape(11),
            placeholder = { Text(text = stringResource(id = R.string.localData))},
            maxLines = 3
        )
    }
}

//儲存案件的bar
@Composable
fun SaveBar(modifier: Modifier) {
    Column (modifier = modifier) {
        Button(onClick = {},
            modifier = Modifier
                .fillMaxHeight()
                .align(CenterHorizontally),
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.tertiary),
            border = BorderStroke(width = 1.dp, color = Color.Black)
        ){
        }
    }
}

//中心區域
@Composable
fun MiddleArea(modifier: Modifier){
    Box(modifier = modifier
        .padding(20.dp)
        .fillMaxWidth()
        .height(400.dp)
    ) {
        Column(horizontalAlignment = Alignment.Start,
            modifier = Modifier
                .height(300.dp)
                .clip(shape = RoundedCornerShape(12))   //切割出圓角
                .background(MaterialTheme.colorScheme.secondary)
                .align(Alignment.Center)
                .border(width = 3.dp, color = Color.Black, shape = RoundedCornerShape(12))
                .padding(20.dp)
        ) {
            var heightOfHint by remember { mutableStateOf(25) }
            var isDown by remember { mutableStateOf(false) }

            InputHint(
                Modifier
                    .weight(1f)
                    .fillMaxSize(), isDown, heightOfHint,
                initHeight = {heightOfHint = it},
                lowerHeight = {
                    heightOfHint = (heightOfHint * 0.9f).toInt()
                    isDown = true   //下降調整過
                }
            )
            ForeignData(modifier = Modifier
                .weight(4f)
                .fillMaxSize()
                .padding(4.dp))
            LocalData(modifier = Modifier
                .weight(4f)
                .fillMaxSize()
                .padding(4.dp))
            SaveBar(modifier = Modifier
                .weight(1.5f)
                .fillMaxSize()
                .padding(3.5.dp))
        }
    }
}

@Composable
fun DownBar(modifier: Modifier,
            goToWordPage: () -> Unit) {
    Row (modifier = modifier
        .fillMaxWidth()
        .height(70.dp)
        .padding(5.dp)
    ){
        Button(onClick = goToWordPage,
            Modifier
                .weight(1f)
                .padding(1.dp)
                .fillMaxHeight(),
            colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.primary),
            border = BorderStroke(width = 1.dp, color = MaterialTheme.colorScheme.tertiary),
            shape = RoundedCornerShape(5.dp),
        ) {
            Text(
                text = stringResource(id = R.string.wordPage),
                color = Color.Black,
                fontFamily = FontFamily.Cursive,
                fontSize = 17.sp,
            )
        }
        Button(onClick = { /*TODO*/ },
            Modifier
                .weight(1f)
                .padding(1.dp)
                .fillMaxHeight(),
            colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.primary),
            border = BorderStroke(width = 1.dp, color = MaterialTheme.colorScheme.tertiary),
            shape = RoundedCornerShape(5.dp),
        ) {
            Text(
                text = stringResource(id = R.string.emptyString),
                color = Color.Black,
                fontFamily = FontFamily.Cursive,
                fontSize = 17.sp,
            )
        }
        Button(onClick = { /*TODO*/ },
            Modifier
                .weight(1f)
                .padding(1.dp)
                .fillMaxHeight(),
            colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.primary),
            border = BorderStroke(width = 1.dp, color = MaterialTheme.colorScheme.tertiary),
            shape = RoundedCornerShape(5.dp),
        ) {
            Text(text = stringResource(id = R.string.emptyString),
                color = Color.Black,
                fontFamily = FontFamily.Cursive,
                fontSize = 17.sp,
            )
        }
        Button(onClick = { /*TODO*/ },
            Modifier
                .weight(1f)
                .padding(1.dp)
                .fillMaxHeight(),
            colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.primary),
            border = BorderStroke(width = 1.dp, color = MaterialTheme.colorScheme.tertiary),
            shape = RoundedCornerShape(5.dp),
        ) {
            Text(text = stringResource(id = R.string.emptyString),
                color = Color.Black,
                fontFamily = FontFamily.Cursive,
                fontSize = 17.sp,
            )
        }
    }
}

//單字輸入頁面
@Composable
fun WordInput(
    modifier: Modifier = Modifier,
    goToWordPage: ()->Unit
){
    Surface(
        modifier = modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        //用於排版的box
        Box(modifier = modifier) {
            MiddleArea(modifier = modifier.align(Alignment.Center))
            DownBar(modifier = modifier.align(Alignment.BottomCenter),
                goToWordPage = goToWordPage)
        }
    }
}

//預覽
/*@Preview(
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    name = "Dark"
)*/
@Preview(
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    name = "light"
)
@Composable
fun PreviewWordInput() {
    TWMATheme {
        WordInput(goToWordPage = {})
    }
}