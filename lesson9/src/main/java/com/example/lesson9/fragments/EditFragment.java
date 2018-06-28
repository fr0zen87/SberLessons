package com.example.lesson9.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.lesson9.R;

public class EditFragment extends Fragment {

    private EditText nameEditView;
    private EditText contentEditView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.edit_fragment, container, false);

        nameEditView = view.findViewById(R.id.name_edit);
        contentEditView = view.findViewById(R.id.content_edit);

        nameEditView.addTextChangedListener(nameTextWatcher);
        contentEditView.addTextChangedListener(contentTextWatcher);

        return view;
    }

    TextWatcher nameTextWatcher = new TextWatcher() {

        TextView nameView = null;

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (nameView == null) {
                nameView = getActivity().findViewById(R.id.note_name);
                if (nameView != null) {
                    nameView.setText(nameEditView.getText());
                }
            } else {
                nameView.setText(nameEditView.getText());
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    TextWatcher contentTextWatcher = new TextWatcher() {

        TextView contentView = null;

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (contentView == null) {
                contentView = getActivity().findViewById(R.id.note_content);
                if (contentView != null) {
                    contentView.setText(contentEditView.getText());
                }
            } else {
                contentView.setText(contentEditView.getText());
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };
}
