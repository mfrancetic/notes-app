package com.example.notesapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<String> notesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getNotesList();
        setupRecyclerView();
    }

    private void getNotesList() {
        notesList = SharedPreferencesHelper.getNoteListFromSharedPreferences(this);
        if (notesList.isEmpty()) {
            notesList.add("Note 1");
            notesList.add("Note 2");
            notesList.add("Note 3");
        }
        SharedPreferencesHelper.saveNoteListToSharedPreferences(notesList, this);
    }

    private void setupRecyclerView() {
        RecyclerView notesRecyclerView = findViewById(R.id.notes_recycler_view);

        NotesAdapter.RecyclerViewClickListener onClickListener = new NotesAdapter.RecyclerViewClickListener() {
            @Override
            public void onClick(View view, int position) {
                Intent openDetailActivityIntent = new Intent(MainActivity.this, NoteDetailActivity.class);
                openDetailActivityIntent.putExtra(Constants.NOTE_KEY, position);
                startActivity(openDetailActivityIntent);
            }
        };

        RecyclerView.Adapter adapter = new NotesAdapter(notesList, onClickListener);
        notesRecyclerView.setAdapter(adapter);

        notesRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(notesRecyclerView.getContext(),
                LinearLayoutManager.VERTICAL);
        notesRecyclerView.addItemDecoration(dividerItemDecoration);
    }
}