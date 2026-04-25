package com.plataformas.todo.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.plataformas.todo.data.Todo
import com.plataformas.todo.data.TodoRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

enum class TodoFilter {
    ALL, PENDING, COMPLETED
}

class TodoViewModel(private val repository: TodoRepository) : ViewModel() {

    private val _filter = MutableStateFlow(TodoFilter.ALL)
    val filter: StateFlow<TodoFilter> = _filter

    // Combine the filter and the database stream to expose the filtered list
    val todos: StateFlow<List<Todo>> = combine(
        repository.getAllTodosStream(),
        _filter
    ) { todos, filter ->
        when (filter) {
            TodoFilter.ALL -> todos
            TodoFilter.PENDING -> todos.filter { !it.isCompleted }
            TodoFilter.COMPLETED -> todos.filter { it.isCompleted }
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    fun setFilter(filter: TodoFilter) {
        _filter.value = filter
    }

    fun saveTodo(id: Int, title: String, description: String) {
        viewModelScope.launch {
            if (id == 0) {
                repository.insertTodo(Todo(title = title, description = description))
            } else {
                repository.updateTodo(Todo(id = id, title = title, description = description, isCompleted = false))
                // Note: we might want to keep the current isCompleted state, but for edit we simplify or fetch.
                // Let's improve this: if editing, the UI should pass the current todo or we just let Room update.
            }
        }
    }

    fun updateTodo(todo: Todo) {
        viewModelScope.launch {
            repository.updateTodo(todo)
        }
    }

    fun toggleTodoCompletion(todo: Todo) {
        viewModelScope.launch {
            repository.updateTodo(todo.copy(isCompleted = !todo.isCompleted))
        }
    }

    fun deleteTodo(todo: Todo) {
        viewModelScope.launch {
            repository.deleteTodo(todo)
        }
    }

    companion object {
        fun provideFactory(repository: TodoRepository): ViewModelProvider.Factory =
            object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    if (modelClass.isAssignableFrom(TodoViewModel::class.java)) {
                        return TodoViewModel(repository) as T
                    }
                    throw IllegalArgumentException("Unknown ViewModel class")
                }
            }
    }
}
