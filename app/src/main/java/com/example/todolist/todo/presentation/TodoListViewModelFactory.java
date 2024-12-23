package com.example.todolist.todo.presentation;

import androidx.annotation.NonNull;
import androidx.lifecycle.AbstractSavedStateViewModelFactory;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;

import com.example.todolist.todo.data.TodoRepository;

public class TodoListViewModelFactory extends AbstractSavedStateViewModelFactory {

    private final TodoRepository todoRepository;

    public TodoListViewModelFactory(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    @SuppressWarnings("unchecked")
    @NonNull
    @Override
    protected <T extends ViewModel> T create(@NonNull String s, @NonNull Class<T> aClass, @NonNull SavedStateHandle savedStateHandle) {
        return (T) new TodoListViewModel(todoRepository);
    }
}
