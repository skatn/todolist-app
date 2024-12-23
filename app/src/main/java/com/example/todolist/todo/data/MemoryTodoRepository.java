package com.example.todolist.todo.data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class MemoryTodoRepository implements TodoRepository {

    private final List<Todo> todos = new ArrayList<>();

    @Override
    public List<Todo> findAll() {
        return todos;
    }

    @Override
    public Todo findById(int id) {
        return todos.get(id);
    }

    @Override
    public List<Todo> findByCompleted(boolean completed) {
        return todos.stream()
                .filter(todo -> todo.isCompleted() == completed)
                .collect(Collectors.toList());
    }

    @Override
    public void save(Todo todo) {
        todos.add(todo);
    }

    @Override
    public void update(int position, Todo updateTodo) {
        Todo findTodo = todos.get(position);
        findTodo.setText(updateTodo.getText());
        findTodo.setCompleted(updateTodo.isCompleted());
    }
}
