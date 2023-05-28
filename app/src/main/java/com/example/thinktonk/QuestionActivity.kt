package com.example.thinktonk

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Divider
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role.Companion.Image
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.thinktonk.ui.theme.ThinkTonkTheme

class QuestionActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ThinkTonkTheme {
                QuestionTonkActivityContent()
            }
        }
    }
}

@Composable
fun QuestionTonkActivityContent() {
    MyCustomLayout()
}
@Composable
fun MyCustomLayout() {
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color(0xFFFBF9F1)),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = "Gods",
                modifier = Modifier
                    .padding(16.dp)
                    .align(Alignment.CenterHorizontally),
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )
            Text(
                text = "3/10",
                modifier = Modifier.padding(start = 10.dp),
                fontWeight = FontWeight.Normal,
                fontSize = 16.sp
            )
            Image(
                painter = painterResource(R.mipmap.question_image),
                contentDescription = "My Image",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .padding(start = 10.dp, end = 10.dp),
                contentScale = ContentScale.Crop
            )
            Text(
                text = "Think",
                modifier = Modifier.padding(start = 16.dp, top = 10.dp),
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )
            Text(
                text = "What is the main Deity of Norse Mithology?",
                modifier = Modifier.padding(start = 16.dp, bottom = 16.dp),
                fontWeight = FontWeight.Normal,
                fontSize = 20.sp
            )
            AnswerList()
        }
        FloatingActionButton(
            onClick = {
                // Handle button click here
            },
            containerColor = Color(0xff7879F1),
            contentColor = Color.Black,
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(16.dp)
                .width(100.dp)
                .height(50.dp)
        ) {
            Text(text = "Tonkn't")
        }
        FloatingActionButton(
            onClick = {
                // Handle button click here
            },
            containerColor = Color(0xffCDEF84),
            contentColor = Color.Black,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp)
                .width(100.dp)
                .height(50.dp)
        ) {
            Text(text = "Tonk")
        }
    }
}
@Composable
fun AnswerList() {
    val itemsList = listOf(
        Subject("Odin"),
        Subject("Hera"),
        Subject("Thor"),
        Subject("Friga"),
    )
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(0.dp)
    ) {
        items(itemsList) { item ->
            Divider(
                color = Color.Gray,
                modifier = Modifier
                    .fillMaxWidth()
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { /* Handle item click */ }
                    .padding(bottom = 25.dp)
                    .height(50.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = item.subject,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 30.dp)
                )
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewQuestionTonkActivityContent() {
    ThinkTonkTheme {
        QuestionTonkActivityContent()
    }
}
