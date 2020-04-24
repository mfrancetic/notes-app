package com.example.notesapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import java.util.ArrayList;

import static com.example.notesapp.Constants.NOTE_KEY;

public class NoteDetailActivity extends AppCompatActivity {

    private ArrayList<String> notesList;
    private int notePosition;
    private EditText editNoteView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_detail);

        setupNoteDetailView();
        setNoteInformation();
    }

    private void setupNoteDetailView() {
        editNoteView = findViewById(R.id.note_edit_text);
        ImageButton saveNoteButton = findViewById(R.id.note_save_button);

        saveNoteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newNoteText = editNoteView.getText().toString();

                if (notePosition != -1) {
                    notesList.remove(notePosition);
                    notesList.add(notePosition, newNoteText);
                } else {
                    notesList.add(newNoteText);
                }

                Intent backToDetailActivityIntent = new Intent(NoteDetailActivity.this, MainActivity.class);
                SharedPreferencesHelper.saveNoteListToSharedPreferences(notesList, NoteDetailActivity.this);
                startActivity(backToDetailActivityIntent);
            }
        });
    }

    private void setNoteInformation() {
        notesList = SharedPreferencesHelper.getNoteListFromSharedPreferences(this);

        final Intent intent = getIntent();
        notePosition = intent.getIntExtra(NOTE_KEY, -1);

        if (notePosition != -1 && notesList.size() > 0) {
            editNoteView.setText(notesList.get(notePosition));
        }
    }
}