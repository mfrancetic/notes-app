package com.example.notesapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Layout;
import android.util.LayoutDirection;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView notesRecyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter adapter;
    private ArrayList<String> notesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        notesRecyclerView = findViewById(R.id.notes_recycler_view);

        notesList = new ArrayList<>();
        notesList.add("Note 1");
        notesList.add("Note 2");
        notesList.add("Note 3");

        adapter = new NotesAdapter(notesList);
        notesRecyclerView.setAdapter(adapter);

        layoutManager = new LinearLayoutManager(this);
        notesRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(notesRecyclerView.getContext(),
                LinearLayoutManager.VERTICAL);
        notesRecyclerView.addItemDecoration(dividerItemDecoration);

        notesList.add("Note 4");
        adapter.notifyDataSetChanged();
    }
}