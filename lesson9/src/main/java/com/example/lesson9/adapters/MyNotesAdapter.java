package com.example.lesson9.adapters;

import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.lesson9.R;
import com.example.lesson9.entities.MyNote;

import java.util.List;

public class MyNotesAdapter extends RecyclerView.Adapter<MyNotesAdapter.ViewHolder> {

    private List<MyNote> notes;

    public MyNotesAdapter(List<MyNote> notes) {
        this.notes = notes;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        final MyNote myNote = notes.get(position);
        holder.name.setText(myNote.getName());
        holder.date.setText(myNote.getDate());
        holder.content.setText(myNote.getContent());

        initStyles(holder);
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView name;
        TextView date;
        TextView content;

        ViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.note_name);
            date = itemView.findViewById(R.id.note_date);
            content = itemView.findViewById(R.id.note_content);
        }
    }

    public List<MyNote> getNotes() {
        return notes;
    }

    private void initStyles(ViewHolder holder) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(holder.itemView.getContext());
        String textSize = preferences.getString("text_size", "14");
        holder.name.setTextSize(Float.parseFloat(textSize));
        holder.date.setTextSize(Float.parseFloat(textSize));
        holder.content.setTextSize(Float.parseFloat(textSize));

        String textStyle = preferences.getString("text_style", "Обычный");
        int typeFace = Typeface.NORMAL;
        if (textStyle.contains("Полужирный")) {
            typeFace += Typeface.BOLD;
        }
        if (textStyle.contains("Курсив")) {
            typeFace += Typeface.ITALIC;
        }
        holder.name.setTypeface(null, typeFace);
        holder.date.setTypeface(null, typeFace);
        holder.content.setTypeface(null, typeFace);
    }
}
