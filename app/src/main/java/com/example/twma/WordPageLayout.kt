package com.example.twma

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.content.res.Resources
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.twma.ui.theme.TWMATheme

fun Int.PxtoDp(): Int = (this / Resources.getSystem().displayMetrics.density).toInt()

//單個單字的UI
@Composable
fun WordContent(word: Word, IsDeleted: ()->Unit, isDeleted: Boolean) {
    var expanded by remember {
        //紀錄更多資料介面 是否打開
        mutableStateOf(false)
    }

    Row(modifier = Modifier
        .padding(3.dp)  //在四周留下特定空間(出現邊界)
        .animateContentSize(
            //彈簧效果
            //確保不會因為彈簧動畫 導致填充變成負數
            animationSpec = spring(
                dampingRatio = Spring.DampingRatioMediumBouncy,
                stiffness = Spring.StiffnessLow
            )
        )
        .clickable { if (!isDeleted) expanded = !expanded }
        .fillMaxWidth(), //點擊展開
        horizontalArrangement = Arrangement.SpaceBetween
    ){
        var colHeight by remember {
            mutableStateOf(90)
        }

        Column(modifier = Modifier
            .padding(12.dp)
            .fillMaxHeight()
            .weight(1f)
            .onGloballyPositioned { colHeight = it.size.height.PxtoDp() }
        ) {

            //生成外國語言及本地語言的內容
            Text(
                text = word.localData,
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = word.foreignData,
                /* 設定樣式(從material中拿出來用 比較彈性化)
                 * 利用.copy來額外的修改細部設定，能在保持彈性的情況下，使用稍微不同的設定
                 * */
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontWeight = FontWeight.ExtraBold   //粗體
                )
            )

            //顯示展開內容
            if(expanded) {
                //extra information
                Text(text = word.extraInf,
                    fontFamily = FontFamily.SansSerif
                )
                //答對率
                Text(
                    text = stringResource(id = R.string.correctRate) + word.correctRate.toString(),
                    fontSize = 8.sp
                )
            }
        }

        Column (modifier = Modifier
            .height(colHeight.dp)
            .weight(0.08f),
            verticalArrangement = Arrangement.SpaceBetween){
            //顯示更多資訊的 icon圖標
            Icon(
                painter = if(expanded) painterResource(id = R.drawable.baseline_expand_less_24) else painterResource(
                    id = R.drawable.baseline_expand_more_24
                ),
                contentDescription = if(expanded) {
                    stringResource(id = R.string.show_less)
                }else {
                    stringResource(id = R.string.show_more)
                },
                modifier = Modifier.fillMaxWidth()
            )

            Icon(
                painter = painterResource(id = R.drawable.baseline_close_24),
                contentDescription = stringResource(id = R.string.deleteWord),
                modifier = Modifier
                    .alpha(0.55f)
                    .fillMaxWidth()
                    .clickable { /*delete word*/
                        Thread() {
                            IsDeleted()    //標記當前單字被刪除
                            userDao.delete(wordId = word.wordId) //更新資料庫
                            wordList.RenewList()    //更新單字列表
                        }.start()
                    }
            )
        }
    }
}

//單字卡
@Composable
fun WordCard(word: Word) {

    var isDeleted by remember {
        mutableStateOf(false)
    }

    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primary
        ),
        modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp).alpha(if(!isDeleted) 1f else 0.3f)
    ) {
        WordContent(word = word, {isDeleted = true}, isDeleted)
    }
}

//單字集合的UI
@Composable
fun WordList(
    modifier: Modifier = Modifier,
    wordList: List<Word> = List(1) {Word()}
) {
    LazyColumn(modifier = modifier.padding(vertical = 4.dp)) {
        //遍歷輸出所有word
        items(items = wordList){
            word -> WordCard(word = word)   //顯示單字的函數
        }
    }
}

//返回鍵
@Composable
fun ReBack(modifier: Modifier, reback: ()->Unit) {
    Button(onClick = reback,
        colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.primary),
        border = BorderStroke(width = 2.dp, color = Color.Black),
        modifier = modifier
            .size(120.dp, 84.dp)
            .padding(2.dp)
            .alpha(7.5f),
        ) {
        Text(text = stringResource(id = R.string.reBack))
    }
}

//單字頁面
@Composable
fun WordPage(modifier: Modifier = Modifier, rebackToWordInput: () -> Unit, wordList: WordList){
    Surface(modifier = modifier, color = MaterialTheme.colorScheme.background) {

        //用於排版的box
        Box(modifier = modifier) {
            if(!wordList.IsEmpty()) WordList(modifier = modifier.fillMaxSize(), wordList = wordList.list)
            else WordList(modifier.fillMaxSize())

            ReBack(modifier = modifier.align(Alignment.BottomEnd),
                reback = rebackToWordInput)
        }
    }
}

//預覽
@Preview(
    showBackground = true,
    uiMode = UI_MODE_NIGHT_YES,
    name = "Dark"
)
@Composable
fun PreviewWordPage() {
    TWMATheme {
        WordPage(rebackToWordInput = {}, wordList = WordList())
    }
}