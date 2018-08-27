package com.example.lesson25;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

public class ImageGalleryAdapter extends RecyclerView.Adapter<ImageGalleryAdapter.MyViewHolder> {

    private List<String> data;
    private Context mContext;

    public ImageGalleryAdapter(Context context, List<String> data) {
        mContext = context;
        this.data = data;
    }

    @NonNull
    @Override
    public ImageGalleryAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageGalleryAdapter.MyViewHolder holder, int position) {
        Uri uri = Uri.parse(data.get(position));
        holder.mPhotoImageView.setImageURI(uri);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public ImageView mPhotoImageView;

        public MyViewHolder(View itemView) {
            super(itemView);
            this.mPhotoImageView = itemView.findViewById(R.id.iv_photo);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION) {
                Intent intent = new Intent(mContext, DetailsActivity.class);
                intent.putStringArrayListExtra("data", (ArrayList<String>) data);
                intent.putExtra("position", position);
                mContext.startActivity(intent);
            }
        }
    }
}