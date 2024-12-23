package com.example.todolist.todo.data;

import java.util.List;

public interface TodoRepository {

    List<Todo> findAll();

    Todo findById(int id);
    List<Todo> findByCompleted(boolean completed);

    void save(Todo todo);
    void update(int id, Todo updateTodo);
}
