package com.example.lesson10.utils;

import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.lesson10.R;
import com.example.lesson10.entities.MyNote;

import java.util.List;

public class MyNotesAdapter extends RecyclerView.Adapter<MyNotesAdapter.ViewHolder> {

    private List<MyNote> mNotes;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        if (mNotes != null) {
            MyNote current = mNotes.get(position);
            holder.name.setText(current.getName());
            holder.date.setText(current.getDate());
            holder.content.setText(current.getContent());

            initStyles(holder);

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    myCallback.onEditClick(mNotes.get(holder.getAdapterPosition()));
                }
            });

            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    myCallback.onDeleteClick(mNotes.get(holder.getAdapterPosition()));
                    return true;
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        if (mNotes != null) {
            return mNotes.size();
        } else return 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView name;
        private TextView date;
        private TextView content;

        ViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.note_name);
            date = itemView.findViewById(R.id.note_date);
            content = itemView.findViewById(R.id.note_content);
        }
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

    public void setNotes(List<MyNote> mNotes) {
        this.mNotes = mNotes;
        notifyDataSetChanged();
    }

    public interface MyCallback {
        void onEditClick(MyNote myNote);
        void onDeleteClick(MyNote myNote);
    }

    private MyCallback myCallback;

    public void registerCallback(MyCallback callback) {
        myCallback = callback;
    }
}
