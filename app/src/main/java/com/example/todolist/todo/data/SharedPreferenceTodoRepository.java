package com.example.todolist.todo.data;

import android.content.Context;
import android.content.SharedPreferences;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SharedPreferenceTodoRepository implements TodoRepository {

    private final SharedPreferences sharedPreferences;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public SharedPreferenceTodoRepository(Context context) {
        sharedPreferences = context.getSharedPreferences("todos", Context.MODE_PRIVATE);
    }

    @Override
    public List<Todo> findAll() {
        return getTodos();
    }

    @Override
    public Todo findById(int id) {
        return getTodos().get(id);
    }

    @Override
    public List<Todo> findByCompleted(boolean completed) {
        return getTodos().stream()
                .filter(todo -> todo.isCompleted() == completed)
                .collect(Collectors.toList());
    }

    @Override
    public void save(Todo todo) {
        List<Todo> todos = getTodos();
        todos.add(todo);
        saveTodos(todos);
    }

    @Override
    public void update(int id, Todo updateTodo) {
        List<Todo> todos = getTodos();
        Todo findTodo = todos.get(id);
        findTodo.setText(updateTodo.getText());
        findTodo.setCompleted(updateTodo.isCompleted());
        saveTodos(todos);
    }

    private List<Todo> getTodos()  {
        String todos = sharedPreferences.getString("todos", null);
        try {
            return todos == null ? new ArrayList<>() : objectMapper.readValue(todos, new TypeReference<List<Todo>>() {});
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private void saveTodos(List<Todo> todos) {
        try {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("todos", objectMapper.writeValueAsString(todos));
            editor.apply();
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
