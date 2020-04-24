package com.example.notesapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import java.util.ArrayList;

public class NoteDetailActivity extends AppCompatActivity {

    private ArrayList<String> notesList;
    private int notePosition;
    private EditText editNoteView;
    private ImageButton saveNoteButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_detail);

        editNoteView = findViewById(R.id.note_edit_text);
        saveNoteButton = findViewById(R.id.note_save_button);

        final Intent intent = getIntent();
        notesList = intent.getStringArrayListExtra(MainActivity.NOTE_LIST_KEY);
        notePosition = intent.getIntExtra(MainActivity.NOTE_KEY, -1);

        if (notePosition != -1) {
            editNoteView.setText(notesList.get(notePosition));
        }

        saveNoteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newNoteText = editNoteView.getText().toString();
                notesList.remove(notePosition);
                notesList.add(notePosition, newNoteText);

                Intent backToDetailActivityIntent = new Intent(NoteDetailActivity.this, MainActivity.class);
                backToDetailActivityIntent.putExtra(MainActivity.NOTE_LIST_KEY, notesList);
                startActivity(backToDetailActivityIntent);
            }
        });
    }
}