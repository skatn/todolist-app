package com.example.todolist.todo.presentation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todolist.R;
import com.example.todolist.todo.data.Todo;

import java.util.ArrayList;
import java.util.List;

public class TodoListAdapter extends RecyclerView.Adapter<TodoListAdapter.Holder> {

    private List<Todo> todos = new ArrayList<>();
    private OnCheckedChangeListener onCheckedChangeListener;


    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_todolist, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TodoListAdapter.Holder holder, int position) {
        holder.onBind(todos.get(position), onCheckedChangeListener);
    }

    @Override
    public int getItemCount() {
        return todos.size();
    }

    public void setTodos(List<Todo> todos) {
        this.todos = todos;
        notifyDataSetChanged();
    }

    public List<Todo> getTodos() {
        return todos;
    }

    public void setOnCheckedChangeListener(OnCheckedChangeListener onCheckedChangeListener) {
        this.onCheckedChangeListener = onCheckedChangeListener;
    }

    public static class Holder extends RecyclerView.ViewHolder {

        private CheckBox checkBox;
        private TextView textView;

        public Holder(@NonNull View itemView) {
            super(itemView);

            checkBox = (CheckBox) itemView.findViewById(R.id.checkbox);
            textView = (TextView) itemView.findViewById(R.id.text);
        }

        public void onBind(Todo todo, OnCheckedChangeListener onCheckedChangeListener) {
            checkBox.setChecked(todo.isCompleted());
            textView.setText(todo.getText());

            checkBox.setOnCheckedChangeListener((compoundButton, b) -> {
                if(compoundButton.isShown()) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION && onCheckedChangeListener != null) {
                        onCheckedChangeListener.onCheckedChange(position, b);
                    }
                }
            });
        }
    }

    public interface OnCheckedChangeListener {
        void onCheckedChange(int position, boolean checked);
    }
}
