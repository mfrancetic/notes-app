package com.example.notesapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.ViewHolder> {

    private ArrayList<String> notesList;
    private RecyclerViewClickListener onClickListener;

    public NotesAdapter(ArrayList<String> notesList, RecyclerViewClickListener onClickListener) {
        this.notesList = notesList;
        this.onClickListener = onClickListener;
    }

    @NonNull
    @Override
    public NotesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View notesView = layoutInflater.inflate(R.layout.notes_recycler_view_item, parent, false);

        return new ViewHolder(notesView, onClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull NotesAdapter.ViewHolder holder, int position) {
        String note = notesList.get(position);

        TextView notesTextView = holder.noteTextView;
        notesTextView.setText(note);
    }

    @Override
    public int getItemCount() {
        if (notesList != null) {
            return notesList.size();
        } else {
            return 0;
        }
    }

    public interface RecyclerViewClickListener {
        void onClick(View view, int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView noteTextView;
        public ImageView notesImageView;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View itemView, RecyclerViewClickListener listener) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);

            onClickListener = listener;
            itemView.setOnClickListener(this);

            noteTextView = itemView.findViewById(R.id.note_text_view);
            notesImageView = itemView.findViewById(R.id.note_image_view);
        }

        @Override
        public void onClick(View v) {
            onClickListener.onClick(v, getAdapterPosition());
        }
    }
};