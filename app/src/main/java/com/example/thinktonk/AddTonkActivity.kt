package com.example.thinktonk

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.materialIcon
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.input.pointer.consumeAllChanges
import androidx.compose.ui.input.pointer.consumePositionChange
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.thinktonk.ui.theme.ThinkTonkTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.UUID
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import org.json.JSONArray
import org.json.JSONObject
import java.io.Console


data class Subject(val subject: String,val id: String = UUID.randomUUID().toString())
val subjects = mutableStateListOf<Subject>()
var thinkName:String="";

class AddTonkActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ThinkTonkTheme {
                AddTonkActivityContent()
            }
        }
    }
}

@Composable
fun AddTonkActivityContent() {
    val context = LocalContext.current;
    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.background(color = Color(0xFFFBF9F1))) {

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "New Think",
                    style = TextStyle(
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    ),
                    modifier = Modifier.padding(10.dp)
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(
                    text = "Think Name",
                    style = TextStyle(
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    ),
                    modifier = Modifier.padding(start = 10.dp)
                )

                Divider(
                    color = Color.Gray,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 10.dp)
                )
                CustomSearch("Name")
                Text(
                    text = "Number of Thinks",
                    style = TextStyle(
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    ),
                    modifier = Modifier.padding(start = 10.dp, top = 10.dp)
                )
                Divider(
                    color = Color.Gray,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 10.dp)
                )
                QuantitySelector(
                    initialQuantity = 5,
                    onQuantityChange = { newQuantity ->
                        // Handle the new quantity here
                        println("New quantity: $newQuantity")
                    }
                )
                Text(
                    text = "Tonk",
                    style = TextStyle(
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    ),
                    modifier = Modifier.padding(start = 10.dp)
                )

                Divider(
                    color = Color.Gray,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 10.dp)
                )
                PlusSearchBar()
                Divider(
                    color = Color.Transparent,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(5.dp)
                )
                SubjetsList()
            }
        }
        FloatingActionButton(
            onClick = {
                val client = OkHttpClient()
                val mediaType = "application/json".toMediaTypeOrNull()
                val apiKey = "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"//"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"

                val requestBody = JSONObject()
                    .put("model", "text-davinci-edit-001")
                    .put("input", "What day of the week is it?")
                    .put("instruction", "Fix the spelling mistakes")
                val request = Request.Builder()
                    .url("https://api.openai.com/v1/edits")
                    .addHeader("Authorization", "Bearer $apiKey")
                    .post(RequestBody.create(mediaType, requestBody.toString()))
                    .build()

                val scope = CoroutineScope(Dispatchers.Main)

                scope.launch {
                    try {
                        withContext(Dispatchers.IO) {
                            val response = client.newCall(request).execute()
                            val responseJson = JSONObject(response.body?.string())

                            println(responseJson.toString())
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
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
            Text(text = "Tonkn't")
        }
        FloatingActionButton(
            onClick = {

            },
            containerColor = Color(0xffCDEF84),
            contentColor = Color.Black,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp)
                .width(100.dp)
                .height(50.dp)
        ) {
            Text(text = "Tonkt")
        }

    }
}


@Composable
fun SubjetsList() {

    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        items(subjects) { item ->
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
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 30.dp)
                )
                IconButton(
                    onClick = { subjects.remove(item)}
                ) {
                    Icon(
                        Icons.Default.Delete,
                        contentDescription = "Delete",
                        tint = Color.Black
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomSearch(text:String) {
    val textFieldValue = remember { mutableStateOf("") }
    thinkName=textFieldValue.value;
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Surface(
            modifier = Modifier
                .weight(1f)
                .width(30.dp)
                .padding(start = 10.dp, end = 10.dp),
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
                    Text(text = text)
                }
            )
        }
    }
}

@Composable
fun QuantitySelector(
    initialQuantity: Int = 0,
    onQuantityChange: (Int) -> Unit
) {
    val quantityState = remember { mutableStateOf(initialQuantity) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .height(56.dp)
                .horizontalScroll(rememberScrollState())
                .pointerInput(Unit) {
                    detectHorizontalDragGestures { change, dragAmount ->
                        val delta = if (dragAmount > 0) 1 else -1
                        quantityState.value += delta
                        change.consumePositionChange()
                    }
                }
        ) {
            IconButton(
                onClick = { if (quantityState.value > 0) quantityState.value-- },
                enabled = quantityState.value > 0,
                modifier = Modifier.size(36.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.mipmap.back_icon),
                    contentDescription = "Decrement",
                    tint = Color.Black
                )
            }
            Box(
                modifier = Modifier
                    .border(1.dp, Color.Black, shape = CircleShape)
                    .size(40.dp),
                        contentAlignment = Alignment.Center

            ) {
                Text(
                    text = quantityState.value.toString(),
                    modifier = Modifier.padding(horizontal = 8.dp)
                )
            }
            IconButton(
                onClick = { quantityState.value++ },
                modifier = Modifier.size(36.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.mipmap.fwd_icon),
                    contentDescription = "Increment",
                    tint = Color.Black
                )
            }
        }
    }

    onQuantityChange(quantityState.value)
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlusSearchBar() {
    val textFieldValue = remember { mutableStateOf("") }
    val context = LocalContext.current
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
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
                    Text(text = "Think")
                },
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 10.dp)
            )

            IconButton(onClick = {
                if(textFieldValue.value.isNullOrEmpty()){
                    Toast.makeText(context, "Think empty", Toast.LENGTH_SHORT).show()
                }else{
                    subjects.add(Subject(subject=textFieldValue.value))
                }
            }) {
                Icon(
                    Icons.Default.Add, // Replace with your icon resource
                    contentDescription = "Icon",
                    tint = Color.Black,
                    modifier = Modifier
                        .padding(horizontal = 8.dp)
                        .size(30.dp)
                )
            }
        }

    }
}

@Preview(showBackground = true)
@Composable
fun AddTonkActivityPreview() {
    ThinkTonkTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            AddTonkActivityContent()
        }
    }
}