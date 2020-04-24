package com.example.notesapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView notesRecyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter adapter;
    private ArrayList<String> notesList;
    private NotesAdapter.RecyclerViewClickListener onClickListener;
    public static final String NOTE_KEY = "note";
    public static final String NOTE_LIST_KEY = "noteList";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        notesRecyclerView = findViewById(R.id.notes_recycler_view);

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra(NOTE_LIST_KEY)) {
            notesList = intent.getStringArrayListExtra(NOTE_LIST_KEY);
        } else {
            notesList = new ArrayList<>();
            notesList.add("Note 1");
            notesList.add("Note 2");
            notesList.add("Note 3");
        }

        onClickListener = new NotesAdapter.RecyclerViewClickListener() {
            @Override
            public void onClick(View view, int position) {
                Intent openDetailActivityIntent = new Intent(MainActivity.this, NoteDetailActivity.class);
                openDetailActivityIntent.putExtra(NOTE_KEY, position);
                openDetailActivityIntent.putExtra(NOTE_LIST_KEY, notesList);
                startActivity(openDetailActivityIntent);
            }
        };

        adapter = new NotesAdapter(notesList, onClickListener);
        notesRecyclerView.setAdapter(adapter);

        layoutManager = new LinearLayoutManager(this);
        notesRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(notesRecyclerView.getContext(),
                LinearLayoutManager.VERTICAL);
        notesRecyclerView.addItemDecoration(dividerItemDecoration);
    }
}