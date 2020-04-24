package com.example.notesapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<String> notesList;
    private RecyclerView.Adapter adapter;

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
                openDetailActivity(position);
            }
        };

        NotesAdapter.RecyclerViewLongClickListener onLongClickListener = new NotesAdapter.RecyclerViewLongClickListener() {
            @Override
            public void onLongClick(View view, int position) {
                openDeleteNoteDialog(position);
            }
        };

        adapter = new NotesAdapter(notesList, onClickListener, onLongClickListener);
        notesRecyclerView.setAdapter(adapter);

        notesRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(notesRecyclerView.getContext(),
                LinearLayoutManager.VERTICAL);
        notesRecyclerView.addItemDecoration(dividerItemDecoration);
    }

    private void openDeleteNoteDialog(final int position) {
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_menu_delete)
                .setTitle(getString(R.string.delete_note_title))
                .setMessage(getString(R.string.delete_note_message))
                .setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        notesList.remove(position);
                        SharedPreferencesHelper.saveNoteListToSharedPreferences(notesList, MainActivity.this);
                        adapter.notifyItemRemoved(position);
                    }
                })
                .setNegativeButton(getString(R.string.no), null)
                .show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = this.getMenuInflater();
        menuInflater.inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);
        switch (item.getItemId()) {
            case R.id.add_note:
                openDetailActivity(-1);
                return true;
        }
        return false;
    }

    private void openDetailActivity(int notePosition) {
        Intent openDetailActivityIntent = new Intent(MainActivity.this, NoteDetailActivity.class);
        openDetailActivityIntent.putExtra(Constants.NOTE_KEY, notePosition);
        startActivity(openDetailActivityIntent);
    }
}