package com.example.composesample

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.composesample.ui.theme.DailyAndroidDemoTheme
import kotlin.coroutines.coroutineContext

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DailyAndroidDemoTheme {
                // A surface container using the 'background' color from the theme
               MyApp(Modifier.fillMaxSize())
            }
        }
    }
}

@Composable
fun MyApp(modifier: Modifier = Modifier) {
    Surface(
        modifier = modifier,
        color = MaterialTheme.colorScheme.background
    ) {
        Column(modifier = Modifier.padding(
            10.dp
        )) {
            Greeting("Android")
            Greeting("world")
        }
    }
}

@Composable
fun Greeting(name: String = "world") {
    var isExpand by remember { mutableStateOf(true) }
    val extraPadding = if (isExpand) 48.dp else 0.dp
    Surface(modifier = Modifier
        .fillMaxWidth()
        .padding(vertical = 4.dp, horizontal = 8.dp), color = MaterialTheme.colorScheme.primary) {
        Row(modifier = Modifier.padding(10.dp)) {
            Text(modifier = Modifier
                .weight(1f)
                .padding(bottom = extraPadding), text = "Hello $name!")
            ElevatedButton(onClick = { isExpand = !isExpand }) {
                Text(text = if (isExpand) "show more" else "show less")
            }
        }

    }

}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    DailyAndroidDemoTheme {
        MyApp()
    }
}