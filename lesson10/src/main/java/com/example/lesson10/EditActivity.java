package com.example.lesson10;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.lesson10.entities.MyNote;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class EditActivity extends AppCompatActivity {

    private EditText mNameEditView;
    private EditText mContentEditView;
    private MyNote mMyNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            mMyNote = bundle.getParcelable(MainActivity.MY_NOTE);
        }

        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment viewFragment = fragmentManager.findFragmentById(R.id.fr_view);
        Fragment editFragment = fragmentManager.findFragmentById(R.id.fr_edit);

        viewFragment.setArguments(bundle);
        editFragment.setArguments(bundle);

        mNameEditView = editFragment.getActivity().findViewById(R.id.fr_name_edit);
        mContentEditView = editFragment.getActivity().findViewById(R.id.fr_content_edit);

        Button button = findViewById(R.id.save_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent replyIntent = new Intent();
                if (TextUtils.isEmpty(mNameEditView.getText()) || TextUtils.isEmpty(mContentEditView.getText())) {
                    setResult(RESULT_CANCELED, replyIntent);
                } else {
                    SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault());
                    String name = mNameEditView.getText().toString();
                    String date = format.format(new Date());
                    String content = mContentEditView.getText().toString();
                    mMyNote.setName(name);
                    mMyNote.setDate(date);
                    mMyNote.setContent(content);
                    replyIntent.putExtra(MainActivity.MY_NOTE, mMyNote);
                    setResult(RESULT_OK, replyIntent);
                }
                finish();
            }
        });
    }
}
