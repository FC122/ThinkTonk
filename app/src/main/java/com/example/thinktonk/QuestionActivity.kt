package com.example.thinktonk

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.foundation.lazy.itemsIndexed
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
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role.Companion.Image
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.thinktonk.ui.theme.ThinkTonkTheme
import kotlinx.coroutines.runBlocking
import model.Question
import model.Quiz
import repository.QuizRepository
                                    //q ans
val answersToQuestions: HashMap<Int,Int> = HashMap()

private val currentQuestionIndex = mutableStateOf(0)


class QuestionActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val quizId = intent.getLongExtra("quizId", 0)
        setContent {
            ThinkTonkTheme {
                QuestionTonkActivityContent(quizId)
            }
        }
    }
}

@Composable
fun QuestionTonkActivityContent(quizId:Long) {
    MyCustomLayout(quizId)
}
@Composable
fun MyCustomLayout(quizId: Long) {
    val repo = QuizRepository.getInstance(context = LocalContext.current)
    val questions:List<Question> = runBlocking { repo.getQuestionsByQuizId(quizId)}
    //Log.d("TAG",questions.toString())
    var index= currentQuestionIndex.value
    val currentQuestion:Question=questions[index]
    Log.d("TAG", currentQuestion.answer)
    val answers=currentQuestion.answer.dropLast(1).split(",")
    Log.d("TAG",answers[0].toString())
    val quiz:Quiz = runBlocking { repo.getQuizById(quizId) }
    val context:Context= LocalContext.current
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        // Handle the result if needed
    }
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color(0xFFFBF9F1)),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = quiz.name,
                modifier = Modifier
                    .padding(16.dp)
                    .align(Alignment.CenterHorizontally),
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )
            Text(
                text = "${currentQuestionIndex.value+1}/${questions.size}",
                modifier = Modifier.padding(start = 10.dp),
                fontWeight = FontWeight.Normal,
                fontSize = 16.sp
            )
           /* Image(
                painter = painterResource(R.mipmap.question_image),
                contentDescription = "My Image",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .padding(start = 10.dp, end = 10.dp),
                contentScale = ContentScale.Crop
            )*/
            Text(
                text = "Think",
                modifier = Modifier.padding(start = 16.dp, top = 10.dp),
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )
            Text(
                text = currentQuestion.question,
                modifier = Modifier.padding(start = 16.dp, bottom = 16.dp),
                fontWeight = FontWeight.Normal,
                fontSize = 20.sp
            )
            AnswerList(answers)
        }
        FloatingActionButton(
            onClick = {
                if (index > 0) {
                    currentQuestionIndex.value--
                } else {
                   //toast
                }
            },
            containerColor = Color(0xff7879F1),
            contentColor = Color.Black,
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(16.dp)
                .width(100.dp)
                .height(50.dp)
        ) {
            Text(text = "Go Back")
        }
        FloatingActionButton(
            onClick = {
                if (index < questions.size-1) {
                    currentQuestionIndex.value++
                } else {
                    //openResults activity, send quiz id and answers,
                    //crossrefernece it and show the results
                    val intent = Intent(context, ResultsActivity::class.java).apply{
                        putExtra("quizId",quizId)
                        putExtra("answersToQuestions", answersToQuestions)
                    }
                    launcher.launch(intent)
                }
            },
            containerColor = Color(0xffCDEF84),
            contentColor = Color.Black,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp)
                .width(100.dp)
                .height(50.dp)
        ) {
            Text(text = "Next")
        }
    }
}
@Composable
fun AnswerList(itemsList:List<String>) {
    val selectedAnswerIndex = remember { mutableStateMapOf<Int, Int>() }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(0.dp)
    ) {
        itemsIndexed(itemsList) { index,item ->
            Divider(
                color = Color.Gray,
                modifier = Modifier
                    .fillMaxWidth()
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        //fix it so the row darkens when clicked
                        answersToQuestions[currentQuestionIndex.value]=index
                        selectedAnswerIndex[currentQuestionIndex.value]=index
                    }
                    .padding(bottom = 25.dp)
                    .height(50.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Text(
                    text = item,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 30.dp),
                    color = if (selectedAnswerIndex[currentQuestionIndex.value]==index) Color.Gray else Color.Black
                )
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewQuestionTonkActivityContent() {
    ThinkTonkTheme {
        QuestionTonkActivityContent(1)
    }
}
