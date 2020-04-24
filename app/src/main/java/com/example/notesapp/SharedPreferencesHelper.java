package com.example.notesapp;

import android.content.Context;
import android.content.SharedPreferences;

import java.io.IOException;
import java.util.ArrayList;

import static com.example.notesapp.Constants.NOTE_LIST_KEY;
import static com.example.notesapp.Constants.PACKAGE_KEY;

public class SharedPreferencesHelper {

    private static SharedPreferences sharedPreferences;

    public static ArrayList<String> getNoteListFromSharedPreferences(Context context){
        sharedPreferences = getSharedPreferences(context);
        ArrayList<String> notesList = new ArrayList<>();
        try {
            notesList = (ArrayList<String>) ObjectSerializer.deserialize
                    (sharedPreferences.getString(NOTE_LIST_KEY, ObjectSerializer.serialize(new ArrayList<>())));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return notesList;
    }

    public static void saveNoteListToSharedPreferences(ArrayList<String> notesList, Context context) {
        sharedPreferences = getSharedPreferences(context);
        try {
            sharedPreferences.edit().putString(NOTE_LIST_KEY, ObjectSerializer.serialize(notesList)).apply();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static SharedPreferences getSharedPreferences(Context context){
       return context.getSharedPreferences(PACKAGE_KEY, Context.MODE_PRIVATE);
    }
}