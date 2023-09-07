package de.stubbe.lstodo

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import de.stubbe.lstodo.extensions.getColorRes
import de.stubbe.lstodo.extensions.getStrRes
import de.stubbe.lstodo.mvp.Presenter
import de.stubbe.lstodo.mvp.Todo
import de.stubbe.lstodo.mvp.TodoContract
import de.stubbe.lstodo.ui.bottomnavigation.BottomNavigation
import de.stubbe.lstodo.ui.list.ToDoList

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // A surface container using the 'background' color from the theme
            Surface(
                modifier = Modifier.fillMaxSize(),
            ) {
                val todoList = remember { mutableStateListOf<Todo>() }
                val sortType = remember { mutableStateOf("") }

                val presenter = remember { Presenter(
                    object : TodoContract.View {
                        override fun submitTodoList(todos: MutableList<Todo>?) {
                            todoList.clear()
                            todoList.addAll(todos ?: mutableListOf())
                        }

                        override fun setNewSortType(newSortType: String?) {
                            sortType.value = newSortType ?: ""
                        }
                    }
                ) }
                Scaffold(
                    containerColor = R.color.light_main_3.getColorRes(),
                    bottomBar = {
                        BottomNavigation(presenter, sortType.value)
                    },
                    content = {
                        Modifier.padding(it)
                        ToDoList(todoList, presenter)
                    },
                    topBar = {
                        TopAppBar(
                            title = {
                                Text(
                                    modifier = Modifier
                                        .padding(8.dp)
                                        .fillMaxWidth(),
                                    text = R.string.app_name.getStrRes(),
                                    color = R.color.light_text.getColorRes(),
                                    fontWeight = FontWeight.Bold,
                                    textAlign = TextAlign.Center,
                                    fontSize = 24.sp,
                                )
                            },
                            colors = TopAppBarDefaults.smallTopAppBarColors(
                                containerColor = R.color.light_main_1.getColorRes(),
                            )
                        )
                    }
                )
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Greeting("Android")
}