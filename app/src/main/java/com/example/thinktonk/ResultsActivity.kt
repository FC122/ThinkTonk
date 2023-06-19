package com.example.thinktonk

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.thinktonk.ui.theme.ThinkTonkTheme
import kotlinx.coroutines.runBlocking
import model.Quiz
import repository.QuizRepository

class ResultsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val quizId = intent.getLongExtra("quizId",0)
        val answersList = intent.getSerializableExtra("answersToQuestions") as HashMap<Int,Int>
        setContent {
            ThinkTonkTheme {
                ResultsActivityContent(quizId, answersList)
            }
        }
    }
}

@Composable
 fun ResultsActivityContent(quizId:Long,answerList: HashMap<Int,Int>) {
    val repo = QuizRepository.getInstance(context = LocalContext.current)//listOf<Quiz>()
    val list = runBlocking {
        repo.getQuestionsByQuizId(quizId)
    }
    val quiz = runBlocking {
        repo.getQuizById(quizId)
    }
    val answers:MutableList<Answer> = mutableListOf()
    var ctrTrue=0
    for(i in 0 until list.size){
        if(list[i].correctAnswerIndex==answerList[i]){
            answers.add(Answer(true,list[i].question))
            ctrTrue++
        }else{
            answers.add(Answer(false,list[i].question))
        }
    }

    Box(modifier = Modifier.padding(10.dp)) {
        Column {
            Text(
                text = quiz.name,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(top = 16.dp),
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )
            Text(
                text = "Your Think Tonk Score",
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(top = 16.dp),
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )
            Text(
                text = "$ctrTrue/${list.size}",
                modifier = Modifier
                    .align(Alignment.CenterHorizontally),
                fontWeight = FontWeight.Bold,
                fontSize = 25.sp
            )
            QuestionList(answers)
        }
        HoveringButtonMy(Modifier.align(Alignment.BottomEnd))
    }
}
@Composable
fun HoveringButtonMy(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        // Handle the result if needed
    }
    Row(
        modifier = modifier
            .background(color = Color(0xFFCDEF84), shape = RoundedCornerShape(percent = 25))
            .size(width = 130.dp, height = 60.dp)
            .clickable {
                val intent = Intent(context, MainActivity::class.java)
                launcher.launch(intent)
            },
        verticalAlignment = Alignment.CenterVertically // Align content vertically
    ) {
        Icon(
            painter = painterResource(id = R.mipmap.think_tonk_icon),
            contentDescription = "Add",
            modifier = Modifier.size(40.dp) // Set the size of the icon
        )
        Text(
            text = "Main",
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(start = 8.dp, end = 16.dp) // Add padding between icon and text
        )
    }
}
@Composable
fun QuestionList(answers:List<Answer>) {

        /* listOf(
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

    )*/

    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        items(answers) { item ->
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





