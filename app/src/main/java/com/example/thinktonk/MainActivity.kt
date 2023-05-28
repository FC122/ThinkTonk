package com.example.thinktonk

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
import androidx.compose.runtime.mutableStateListOf
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

data class ThinkTonk(val number: String, val title: String, val description: String)
val thinkTonks = mutableStateListOf<ThinkTonk>()
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ThinkTonkTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    DisplayImageWithText()
                }
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomSearchBar() {
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
            color = Color(0xffeae8e0)// Set the background color here
        ) {
            TextField(
                value = textFieldValue.value,
                onValueChange = { textFieldValue.value = it },

                colors = TextFieldDefaults.textFieldColors(
                    textColor = Color.Gray,
                    cursorColor = Color.White,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                shape = RoundedCornerShape(28.dp),
                placeholder = {
                    Text(text = "Search your think")
                },
                leadingIcon = {
                    Icon(
                        painter = painterResource(id = R.mipmap.search_icon), // Replace with your icon resource
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
@Composable
fun DisplayWithSearchAndList() {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        CustomSearchBar()
        Spacer(modifier = Modifier.height(16.dp))
        CustomList()
    }
}

@Composable
fun CustomList() {

    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        items(thinkTonks) { item ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { /* Handle item click */ }
                    .padding(bottom = 25.dp)
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = item.title,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )
                    Spacer(modifier = Modifier.height(5.dp))
                    Text(
                        text = item.description,
                        fontSize = 14.sp
                    )
                }
                Text(
                    text = item.number,
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