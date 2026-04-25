package com.plataformas.todo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.plataformas.todo.ui.TodoViewModel
import com.plataformas.todo.ui.screens.TodoListScreen
import com.plataformas.todo.ui.theme.App01Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        
        val app = application as TodoApplication
        val viewModel: TodoViewModel by viewModels { 
            TodoViewModel.provideFactory(app.repository)
        }

        setContent {
            App01Theme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    TodoListScreen(viewModel = viewModel)
                }
            }
        }
    }
}