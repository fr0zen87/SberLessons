package com.example.lesson18;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

public class MyListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    class UriConnectionViewHolder extends RecyclerView.ViewHolder {

        public UriConnectionViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    class PicassoViewHolder extends RecyclerView.ViewHolder {

        public PicassoViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    class GlideViewHolder extends RecyclerView.ViewHolder {

        public GlideViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    class FrescoViewHolder extends RecyclerView.ViewHolder {

        public FrescoViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
