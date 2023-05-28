package com.example.thinktonk

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.thinktonk.ui.theme.ThinkTonkTheme

class ResultsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ThinkTonkTheme {
                ResultsActivityContent()
            }
        }
    }
}

@Composable
 fun ResultsActivityContent() {
    Box(modifier = Modifier.padding(10.dp)) {
        Column {
            Text(
                text = "Gods",
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(top=16.dp),
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )
            Text(
                text = "Your Think Tonk Score",
                modifier = Modifier.align(Alignment.CenterHorizontally)
                    .padding(top=16.dp),
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )
            Text(
                text = "6/10",
                modifier = Modifier
                    .align(Alignment.CenterHorizontally),
                fontWeight = FontWeight.Bold,
                fontSize = 25.sp
            )
            QuestionList()
        }
    }
}

@Composable
fun QuestionList() {
    val itemsList = listOf(
        Answer(true,"Who is Thor"),
        Answer(false,"Who is Odin"),
        Answer(true,"What is the main Deity of Norse Mithology? sdfsfdddsdsadasdasdasdasdasasdsaasasdaasdasdasdadasd"),
        Answer(true,"Who is Thor"),
        Answer(false,"Who is Odin"),
        Answer(true,"What is the main Deity of Norse Mithology? sdfsfdddsdsadasdasdasdasdasasdsaasasdaasdasdasdadasd"),
        Answer(true,"Who is Thor"),
        Answer(false,"Who is Odin"),
        Answer(true,"What is the main Deity of Norse Mithology? sdfsfdddsdsadasdasdasdasdasasdsaasasdaasdasdasdadasd"),
        Answer(true,"Who is Thor"),
        Answer(false,"Who is Odin"),
        Answer(true,"What is the main Deity of Norse Mithology? sdfsfdddsdsadasdasdasdasdasasdsaasasdaasdasdasdadasd"),
        Answer(true,"Who is Thor"),
    )

    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        items(itemsList) { item ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { /* Handle item click */ }
                    .padding(bottom = 25.dp)
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "Think",
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )
                    Spacer(modifier = Modifier.height(5.dp))
                    Text(
                        text = item.question,
                        fontSize = 14.sp
                    )
                }
                if(item.result) {
                    Icon(
                        Icons.Default.Done,
                        contentDescription = "Delete",
                        tint = Color.Black
                    )
                }else{
                    Icon(
                        Icons.Default.Warning,
                        contentDescription = "Delete",
                        tint = Color.Black
                    )
                }
            }
        }
    }
}

data class Answer(val result: Boolean, val question: String)



@Preview(showBackground = true)
@Composable
fun PreviewResultsTonkActivityContent() {
    ThinkTonkTheme {
        ResultsActivityContent()
    }
}

