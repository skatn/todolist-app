package com.example.todolist.todo.presentation;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todolist.R;
import com.example.todolist.todo.data.SharedPreferenceTodoRepository;
import com.example.todolist.todo.data.TabType;
import com.example.todolist.todo.data.Todo;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 뷰 & 변수 초기화
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        EditText editText = (EditText) findViewById(R.id.edit_text);
        Button addButton = (Button) findViewById(R.id.add_button);
        TextView textViewAll = (TextView) findViewById(R.id.tv_all);
        TextView textViewDoesnt = (TextView) findViewById(R.id.tv_doesnt);
        TextView textViewDone = (TextView) findViewById(R.id.tv_done);

        TodoListViewModelFactory factory = new TodoListViewModelFactory(new SharedPreferenceTodoRepository(getApplicationContext()));
        TodoListViewModel todoListViewModel = new ViewModelProvider(this, factory).get(TodoListViewModel.class);
        TodoListAdapter adapter = new TodoListAdapter();



        // viewModel 초기화
        todoListViewModel.getTodos().observe(this, adapter::setTodos);
        todoListViewModel.getTabType().observe(this, tabType -> {
            switch(tabType) {
                case ALL:
                    textViewAll.setTextColor(Color.MAGENTA);
                    textViewDoesnt.setTextColor(Color.BLACK);
                    textViewDone.setTextColor(Color.BLACK);
                    break;
                case DOESNT:
                    textViewAll.setTextColor(Color.BLACK);
                    textViewDoesnt.setTextColor(Color.MAGENTA);
                    textViewDone.setTextColor(Color.BLACK);
                    break;
                case DONE:
                    textViewAll.setTextColor(Color.BLACK);
                    textViewDoesnt.setTextColor(Color.BLACK);
                    textViewDone.setTextColor(Color.MAGENTA);
                    break;
            }
        });



        // recyclerView 초기화
        adapter.setOnCheckedChangeListener((position, checked) -> {
            Todo todo = adapter.getTodos().get(position);
            todoListViewModel.updateTodo(position, new Todo(todo.getText(), checked));
        });
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));



        // todo 추가 버튼 초기화
        addButton.setOnClickListener((view) -> {
            String text = editText.getText().toString();
            todoListViewModel.addTodo(new Todo(text, false));
        });



        // tab 초기화
        textViewAll.setOnClickListener((view) -> {
            todoListViewModel.setTabType(TabType.ALL);
        });
        textViewDoesnt.setOnClickListener((view) -> {
            todoListViewModel.setTabType(TabType.DOESNT);
        });
        textViewDone.setOnClickListener((view) -> {
            todoListViewModel.setTabType(TabType.DONE);
        });
    }
}