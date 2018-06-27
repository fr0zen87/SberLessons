package com.example.lesson9;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

class MyNotesAdapter extends RecyclerView.Adapter<MyNotesAdapter.ViewHolder> {

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
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MyNote myNote = notes.get(position);
        holder.name.setText(myNote.getName());
        holder.date.setText(myNote.getDate());
        holder.content.setText(myNote.getContent());
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView name;
        TextView date;
        TextView content;

        public ViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.note_name);
            date = itemView.findViewById(R.id.note_date);
            content = itemView.findViewById(R.id.note_content);
        }
    }
}
