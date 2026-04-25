package com.plataformas.todo.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.plataformas.todo.data.Todo

@Composable
fun AddEditTodoDialog(
    todoToEdit: Todo? = null,
    onDismiss: () -> Unit,
    onSave: (id: Int, title: String, description: String) -> Unit
) {
    var title by remember { mutableStateOf(todoToEdit?.title ?: "") }
    var description by remember { mutableStateOf(todoToEdit?.description ?: "") }
    var titleError by remember { mutableStateOf(false) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = if (todoToEdit == null) "New Task" else "Edit Task") },
        text = {
            Column(modifier = Modifier.fillMaxWidth()) {
                OutlinedTextField(
                    value = title,
                    onValueChange = { 
                        title = it
                        titleError = it.isBlank()
                    },
                    label = { Text("Title") },
                    singleLine = true,
                    isError = titleError,
                    modifier = Modifier.fillMaxWidth()
                )
                if (titleError) {
                    Text(
                        text = "Title cannot be empty",
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.padding(start = 16.dp, top = 4.dp)
                    )
                }
                
                Spacer(modifier = Modifier.height(8.dp))
                
                OutlinedTextField(
                    value = description,
                    onValueChange = { description = it },
                    label = { Text("Description (Optional)") },
                    modifier = Modifier.fillMaxWidth(),
                    minLines = 3
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    if (title.isNotBlank()) {
                        onSave(todoToEdit?.id ?: 0, title, description)
                    } else {
                        titleError = true
                    }
                }
            ) {
                Text("Save")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}
