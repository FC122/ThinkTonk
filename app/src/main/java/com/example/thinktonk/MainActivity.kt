package com.example.thinktonk

import android.app.Application
import android.content.Context
import android.content.Intent
import android.os.Bundle
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
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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

private val searchQuery = mutableStateOf("")

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //populateDatabaseWithQuizzes(this)
        setContent {
            ThinkTonkTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    DisplayImageWithText()
                }
            }
        }
    }

    fun populateDatabaseWithQuizzes(context: Context) {
        val quizRepository = QuizRepository.getInstance(context)

        runBlocking {
            val quizList = listOf(
                Quiz(name = "Quiz 1", themes = "History"),
                Quiz(name = "Quiz 2", themes = "Science"),
                Quiz(name = "Quiz 3", themes = "Geography"),
                Quiz(name = "Quiz 4", themes = "Math"),
                Quiz(name = "Quiz 5", themes = "Sports")
            )

            for (quiz in quizList) {
                quizRepository.insertQuiz(quiz)
            }

            val allQuizzes = quizRepository.getAllQuizzes()
            println("Quizzes in the database:")
            for (quiz in allQuizzes) {
                println("Quiz ID: ${quiz.id}, Name: ${quiz.name}, Themes: ${quiz.themes}")
            }
        }
    }
}




@Composable
fun DisplayImageWithText() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(0xFFFBF9F1))
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.mipmap.think_tonk_icon),
                contentDescription = "Image",
                modifier = Modifier
                    .size(width = 80.dp, height = 80.dp)
            )
            Spacer(modifier = Modifier.height(1.dp))
            Text(
                text = "Think Tonk",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )
            Spacer(modifier = Modifier.height(16.dp))
            DisplayWithSearchAndList()
        }
        HoveringButton(modifier = Modifier.align(Alignment.BottomEnd))
    }
}
@Composable
fun HoveringButton(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        // Handle the result if needed
    }
    Row(
        modifier = modifier
            .background(color = Color(0xFFCDEF84), shape = RoundedCornerShape(percent = 25))
            .size(width = 130.dp, height = 60.dp)
            .clickable {
                val intent = Intent(context, AddTonkActivity::class.java)
                launcher.launch(intent)
            },
        verticalAlignment = Alignment.CenterVertically // Align content vertically
    ) {
        Icon(
            painter = painterResource(id = R.mipmap.pen_icon),
            contentDescription = "Add",
            modifier = Modifier.size(40.dp) // Set the size of the icon
        )
        Text(
            text = "New Tonk",
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(start = 8.dp, end = 16.dp) // Add padding between icon and text
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomSearchBar(
    onSearchQueryChange: (String) -> Unit
) {
    val textFieldValue = remember { mutableStateOf("") }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Surface(
            modifier = Modifier.weight(1f).width(30.dp),
            shape = RoundedCornerShape(28.dp),
            color = Color(0xffeae8e0)
        ) {
            TextField(
                value = textFieldValue.value,
                onValueChange = {
                    textFieldValue.value = it
                    onSearchQueryChange(it) // Call the provided function with the updated value
                },
                colors = TextFieldDefaults.textFieldColors(
                    textColor = Color.Gray,
                    cursorColor = Color.White,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                shape = RoundedCornerShape(28.dp),
                placeholder = {
                    Text(text = "Search your Thonks")
                },
                leadingIcon = {
                    Icon(
                        painter = painterResource(id = R.mipmap.search_icon),
                        contentDescription = "Icon",
                        tint = Color.Black,
                        modifier = Modifier.padding(start = 16.dp, end = 8.dp).size(40.dp)
                    )
                }
            )
        }
    }
}

@Composable
fun DisplayWithSearchAndList() {
    val searchQuery = remember { mutableStateOf("") }
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        CustomSearchBar(onSearchQueryChange = { query ->
            searchQuery.value = query
        })
        Spacer(modifier = Modifier.height(16.dp))
        CustomList(searchQuery.value)
    }
}

@Composable
fun CustomList(searchQuery: String) {
    val repo = QuizRepository.getInstance(context = LocalContext.current)//listOf<Quiz>()
    val list = runBlocking {
        repo.getAllQuizzes()
    }
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        // Handle the result if needed
    }

    val context = LocalContext.current
    val filteredList = remember(searchQuery, list) {
        list.filter { quiz ->
            quiz.name.contains(searchQuery, ignoreCase = true) ||
                    quiz.themes.contains(searchQuery, ignoreCase = true)
        }
    }
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        items(filteredList) { item ->
            val questions = runBlocking { repo.getQuestionsByQuizId(item.id) }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        //fix it so the quiz loads upon clikcing on the list, try toast id first
                        //maybe its sync
                        val intent = Intent(context, QuestionActivity::class.java)
                        intent.putExtra("quizId", item.id)
                        launcher.launch(intent)
                    }
                    .padding(bottom = 25.dp)
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = item.name,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )
                    Spacer(modifier = Modifier.height(5.dp))
                    Text(
                        text = item.themes,
                        fontSize = 14.sp
                    )
                }
                Text(
                    text = questions.size.toString(),
                    fontSize = 14.sp,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }
        }
    }
}



@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ThinkTonkTheme {
        DisplayImageWithText()
    }
}