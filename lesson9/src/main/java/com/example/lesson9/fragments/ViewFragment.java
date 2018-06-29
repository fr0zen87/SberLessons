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

    private TextView nameView;
    private TextView dateView;
    private TextView contentView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.list_item, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        nameView = view.findViewById(R.id.note_name);
        dateView = view.findViewById(R.id.note_date);
        contentView = view.findViewById(R.id.note_content);
    }

    @Override
    public void onResume() {
        super.onResume();

        Bundle bundle = getArguments();
        if (bundle != null) {
            MyNote myNote = bundle.getParcelable(MainActivity.MY_NOTE);

            nameView.setText(myNote.getName());
            dateView.setText(myNote.getDate());
            contentView.setText(myNote.getContent());
        }
    }
}
