package com.example.notesapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<String> notesList;
    private RecyclerView.Adapter adapter;
    private ImageView emptyImageView;
    private TextView emptyTextView;
    private ImageView noteImageView;
    private FloatingActionButton floatingActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getNotesList();
        setupRecyclerView();
        setupFab();
    }

    private void setupFab() {
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDetailActivity(-1);
            }
        });
    }

    private void getNotesList() {
        notesList = SharedPreferencesHelper.getNoteListFromSharedPreferences(this);

        emptyImageView = findViewById(R.id.empty_image_view);
        emptyTextView = findViewById(R.id.empty_text_view);
        noteImageView = findViewById(R.id.note_image_view);
        floatingActionButton = findViewById(R.id.fab);

        updateEmptyView();
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
                .setIcon(R.mipmap.ic_delete)
                .setTitle(getString(R.string.delete_note_title))
                .setMessage(getString(R.string.delete_note_message))
                .setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        notesList.remove(position);
                        SharedPreferencesHelper.saveNoteListToSharedPreferences(notesList, MainActivity.this);
                        adapter.notifyItemRemoved(position);
                        Toast.makeText(MainActivity.this, getString(R.string.note_deleted), Toast.LENGTH_SHORT).show();
                        updateEmptyView();
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

    private void updateEmptyView(){
        if (notesList.isEmpty()) {
            emptyTextView.setVisibility(View.VISIBLE);
            emptyImageView.setVisibility(View.VISIBLE);
        } else {
            emptyImageView.setVisibility(View.GONE);
            emptyTextView.setVisibility(View.GONE);
        }
    }
}