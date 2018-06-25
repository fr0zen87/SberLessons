package com.example.lesson7.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lesson7.R;
import com.example.lesson7.entities.Phone;

import java.util.List;

public class DataAdapter3 extends RecyclerView.Adapter<DataAdapter3.ViewHolder> {

    private List<Phone> phones;

    public DataAdapter3(List<Phone> phones) {
        this.phones = phones;
    }

    @NonNull
    @Override
    public DataAdapter3.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item3, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DataAdapter3.ViewHolder holder, int position) {
        Phone phone = phones.get(position);
        holder.imageView.setImageResource(phone.getImage());
        holder.nameView.setText(phone.getName());
        holder.companyView.setText(phone.getCompany());
    }

    @Override
    public int getItemCount() {
        return phones.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        final ImageView imageView;
        final TextView nameView;
        final TextView companyView;

        ViewHolder(View view){
            super(view);
            imageView = view.findViewById(R.id.image);
            nameView = view.findViewById(R.id.name);
            companyView = view.findViewById(R.id.company);
        }
    }
}