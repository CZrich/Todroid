package com.plataformas.todo

import android.app.Application
import com.plataformas.todo.data.TodoDatabase
import com.plataformas.todo.data.TodoRepository

class TodoApplication : Application() {
    val database by lazy { TodoDatabase.getDatabase(this) }
    val repository by lazy { TodoRepository(database.todoDao()) }
}
