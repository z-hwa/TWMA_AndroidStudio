package com.example.twma

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.twma.ui.theme.TWMATheme

//單個單字的UI
@Composable
fun WordContent(word: Word) {
    var expanded by remember {
        //紀錄更多資料介面 是否打開
        mutableStateOf(false)
    }

    Row(modifier =
    Modifier
        .padding(5.dp)  //在四周留下特定空間(出現邊界)
        .animateContentSize(
            //彈簧效果
            //確保不會因為彈簧動畫 導致填充變成負數
            animationSpec = spring(
                dampingRatio = Spring.DampingRatioMediumBouncy,
                stiffness = Spring.StiffnessLow
            )
        )
    ){
        Column(modifier = Modifier
            .weight(1f)
            .padding(12.dp)
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
                //答對率
                Text(
                    text = stringResource(id = R.string.correctRate) + word.correctRate.toString()
                )
            }
        }
        //顯示更多資訊的 icon圖標
        IconButton(onClick = {expanded = ! expanded}) {
            Icon(
                painter = if(expanded) painterResource(id = R.drawable.baseline_expand_less_24) else painterResource(
                    id = R.drawable.baseline_expand_more_24
                ),
                contentDescription = if(expanded) {
                    stringResource(id = R.string.show_less)
                }else {
                    stringResource(id = R.string.show_more)
                }
            )
        }
    }
}

//單字卡
@Composable
fun WordCard(word: Word) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primary
        ),
        modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp)
    ) {
        WordContent(word = word)
    }
}

//單字集合的UI
@Composable
fun WordList(
    modifier: Modifier = Modifier,
    wordList: List<Word> = List(100) {Word()}
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
    Button(modifier = modifier
        .size(120.dp, 84.dp)
        .padding(2.dp),
        onClick = reback,
        colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.primary)
        ) {
        Text(text = "Back")
    }
}

//單字頁面
@Composable
fun WordPage(modifier: Modifier = Modifier, rebackToWordInput: () -> Unit){
    Surface(modifier = modifier, color = MaterialTheme.colorScheme.background) {
        //用於排版的box
        Box(modifier = modifier) {
            WordList(modifier = modifier.fillMaxSize())
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
        WordPage(rebackToWordInput = {})
    }
}