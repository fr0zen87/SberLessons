package com.example.lesson9.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.lesson9.MainActivity;
import com.example.lesson9.R;
import com.example.lesson9.entities.MyNote;

public class ViewFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.list_item, container, false);

        Bundle bundle = getArguments();
        TextView nameView = view.findViewById(R.id.note_name);
        TextView dateView = view.findViewById(R.id.note_date);
        TextView contentView = view.findViewById(R.id.note_content);

        MyNote myNote = bundle.getParcelable(MainActivity.MY_NOTE);
        nameView.setText(myNote.getName());
        dateView.setText(myNote.getDate());
        contentView.setText(myNote.getContent());

        return view;
    }
}
