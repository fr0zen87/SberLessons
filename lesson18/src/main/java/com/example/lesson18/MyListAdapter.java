package com.example.lesson18;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class MyListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<String> uris;

    public MyListAdapter(List<String> uris) {
        this.uris = uris;
    }

    @Override
    public int getItemViewType(int position) {
        return position % 4;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item, viewGroup, false);
        switch (viewType) {
            case 0: {
                return new UrlConnectionViewHolder(view);
            }
            case 1: {
                return new PicassoViewHolder(view);
            }
            case 2: {
                return new GlideViewHolder(view);
            }
            case 3: {
                return new FrescoViewHolder(view);
            }
            default: {
                return new UrlConnectionViewHolder(view);
            }
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        switch (viewHolder.getItemViewType()) {
            case 0: {
                try {
                    URL url = new URL(uris.get(position));
                    UrlConnectionViewHolder holder = (UrlConnectionViewHolder) viewHolder;
                    holder.textView.setText(R.string.url_download);
                    holder.imageView.setImageBitmap(getImage(url));
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                break;
            }
            case 1: {
                Uri uri = Uri.parse(uris.get(position));
                PicassoViewHolder holder = (PicassoViewHolder) viewHolder;
                holder.textView.setText(R.string.picasso_download);
                Picasso.get()
                        .load(uri)
                        .resize(50, 50)
                        .into(holder.imageView);
                break;
            }
            case 2: {
                Uri uri = Uri.parse(uris.get(position));
                GlideViewHolder holder = (GlideViewHolder) viewHolder;
                holder.textView.setText(R.string.glide_download);
                GlideApp.with(holder.itemView)
                        .load(uri)
                        .override(60,60)
                        .into(holder.imageView);
                break;
            }
            case 3: {
                Uri uri = Uri.parse(uris.get(position));
                FrescoViewHolder holder = (FrescoViewHolder) viewHolder;
                holder.imageView.setVisibility(View.GONE);
                holder.draweeView.setVisibility(View.VISIBLE);
                holder.textView.setText(R.string.fresco_download);
                holder.draweeView.setImageURI(uri);
                break;
            }
        }
    }

    @Override
    public int getItemCount() {
        return uris.size();
    }

    class UrlConnectionViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView textView;

        UrlConnectionViewHolder(@NonNull View itemView) {
            super(itemView);
            this.imageView = itemView.findViewById(R.id.imageView);
            this.textView = itemView.findViewById(R.id.textView);
        }
    }

    class PicassoViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView textView;

        PicassoViewHolder(@NonNull View itemView) {
            super(itemView);
            this.imageView = itemView.findViewById(R.id.imageView);
            this.textView = itemView.findViewById(R.id.textView);
        }
    }

    class GlideViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView textView;

        GlideViewHolder(@NonNull View itemView) {
            super(itemView);
            this.imageView = itemView.findViewById(R.id.imageView);
            this.textView = itemView.findViewById(R.id.textView);
        }
    }

    class FrescoViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView textView;
        SimpleDraweeView draweeView;

        FrescoViewHolder(@NonNull View itemView) {
            super(itemView);
            this.imageView = itemView.findViewById(R.id.imageView);
            this.textView = itemView.findViewById(R.id.textView);
            this.draweeView = itemView.findViewById(R.id.fresco_imageView);
        }
    }

    private Bitmap getImage(URL url) {
        HttpURLConnection connection = null;
        try {
            connection = (HttpURLConnection) url.openConnection();
            connection.connect();
            int responseCode = connection.getResponseCode();
            if (responseCode == 200) {
                return BitmapFactory.decodeStream(connection.getInputStream());
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
        return null;
    }
}
