package com.plataformas.todo.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.plataformas.todo.data.Todo
import com.plataformas.todo.ui.TodoFilter
import com.plataformas.todo.ui.TodoViewModel
import com.plataformas.todo.ui.components.AddEditTodoDialog
import com.plataformas.todo.ui.components.TodoItemCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoListScreen(viewModel: TodoViewModel) {
    val todos by viewModel.todos.collectAsStateWithLifecycle()
    val currentFilter by viewModel.filter.collectAsStateWithLifecycle()

    var showDialog by remember { mutableStateOf(false) }
    var todoToEdit by remember { mutableStateOf<Todo?>(null) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("My Tasks") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { 
                    todoToEdit = null
                    showDialog = true 
                },
                containerColor = MaterialTheme.colorScheme.tertiaryContainer,
                contentColor = MaterialTheme.colorScheme.onTertiaryContainer
            ) {
                Icon(Icons.Filled.Add, "Add Task")
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            // Filters
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                FilterChip(
                    selected = currentFilter == TodoFilter.ALL,
                    onClick = { viewModel.setFilter(TodoFilter.ALL) },
                    label = { Text("All") }
                )
                FilterChip(
                    selected = currentFilter == TodoFilter.PENDING,
                    onClick = { viewModel.setFilter(TodoFilter.PENDING) },
                    label = { Text("Pending") }
                )
                FilterChip(
                    selected = currentFilter == TodoFilter.COMPLETED,
                    onClick = { viewModel.setFilter(TodoFilter.COMPLETED) },
                    label = { Text("Completed") }
                )
            }

            // List of Tasks
            if (todos.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "No tasks found",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(bottom = 80.dp)
                ) {
                    items(items = todos, key = { it.id }) { todo ->
                        TodoItemCard(
                            todo = todo,
                            onCheckedChange = { viewModel.toggleTodoCompletion(todo) },
                            onEdit = {
                                todoToEdit = todo
                                showDialog = true
                            },
                            onDelete = { viewModel.deleteTodo(todo) }
                        )
                    }
                }
            }
        }
    }

    if (showDialog) {
        AddEditTodoDialog(
            todoToEdit = todoToEdit,
            onDismiss = { showDialog = false },
            onSave = { id, title, description ->
                viewModel.saveTodo(id, title, description)
                showDialog = false
            }
        )
    }
}
