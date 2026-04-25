package com.plataformas.todo.data

import kotlinx.coroutines.flow.Flow

class TodoRepository(private val todoDao: TodoDao) {

    fun getAllTodosStream(): Flow<List<Todo>> = todoDao.getAllTodos()

    fun getTodoStream(id: Int): Flow<Todo> = todoDao.getTodoById(id)

    suspend fun insertTodo(todo: Todo) = todoDao.insertTodo(todo)

    suspend fun deleteTodo(todo: Todo) = todoDao.deleteTodo(todo)

    suspend fun updateTodo(todo: Todo) = todoDao.updateTodo(todo)
}
