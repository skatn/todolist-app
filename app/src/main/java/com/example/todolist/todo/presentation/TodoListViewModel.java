package com.example.todolist.todo.presentation;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.todolist.todo.data.TabType;
import com.example.todolist.todo.data.Todo;
import com.example.todolist.todo.data.TodoRepository;

import java.util.List;

public class TodoListViewModel extends ViewModel {

    private final TodoRepository todoRepository;
    private final MutableLiveData<List<Todo>> todos;
    private final MutableLiveData<TabType> tabType = new MutableLiveData<>(TabType.ALL);

    public TodoListViewModel(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
        todos = new MutableLiveData<>(todoRepository.findAll());
    }

    public LiveData<TabType> getTabType() {
        return tabType;
    }

    public void setTabType(TabType tabType) {
        this.tabType.setValue(tabType);
        notifyUpdate();
    }

    public LiveData<List<Todo>> getTodos() {
        return todos;
    }

    public void addTodo(Todo todo) {
        todoRepository.save(todo);
        notifyUpdate();
    }

    public void updateTodo(int id, Todo updateTodo) {
        todoRepository.update(id, updateTodo);
        notifyUpdate();
    }

    private void notifyUpdate() {
        switch(tabType.getValue()) {
            case ALL:
                todos.setValue(todoRepository.findAll());
                break;
            case DOESNT:
                todos.setValue(todoRepository.findByCompleted(false));
                break;
            case DONE:
                todos.setValue(todoRepository.findByCompleted(true));
                break;
        }
    }
}
