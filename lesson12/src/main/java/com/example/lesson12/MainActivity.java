package com.example.lesson12;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private static final String URL_1 = "content://com.example.lesson9.provider/notes";
    private static final String URL_2 = "content://com.example.lesson10.provider/notes";

    private boolean isUseId = false;
    private int projectToUse = 1;

    private EditText editText;
    private Button deleteButton;
    private Button updateButton;
    private Button insertButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = findViewById(R.id.editText);
        deleteButton = findViewById(R.id.delete_button);
        updateButton = findViewById(R.id.update_button);
        insertButton = findViewById(R.id.insert_button);
    }

    public void idCheckClick(View view) {
        CheckBox checkBox = (CheckBox) view;
        if (checkBox.isChecked()) {
            isUseId = true;
            editText.setVisibility(View.VISIBLE);
            insertButton.setVisibility(View.GONE);
            deleteButton.setVisibility(View.VISIBLE);
            updateButton.setVisibility(View.VISIBLE);
        } else {
            isUseId = false;
            editText.setVisibility(View.INVISIBLE);
            editText.setText("");
            insertButton.setVisibility(View.VISIBLE);
            deleteButton.setVisibility(View.GONE);
            updateButton.setVisibility(View.GONE);
        }
    }

    public void onRadioButtonClicked(View view) {
        RadioButton radioButton = (RadioButton) view;
        radioButton.setChecked(true);
        switch (view.getId()) {
            case R.id.radioButton_lesson9: {
                projectToUse = 1;
                break;
            }
            case R.id.radioButton_lesson10: {
                projectToUse = 2;
                break;
            }
        }
    }

    public void requestClick(View view) {
        Uri requestUri = getRequestUri();
        ContentResolver contentResolver = getContentResolver();
        TextView textView = findViewById(R.id.result_view);
        switch (view.getId()) {
            case R.id.select_button: {
                selectRequest(requestUri, contentResolver, textView);
                break;
            }
            case R.id.delete_button: {
                deleteRequest(requestUri, contentResolver, textView);
                break;
            }
            case R.id.insert_button: {
                insertRequest(requestUri, contentResolver, textView);
                break;
            }
            case R.id.update_button: {
                updateRequest(requestUri, contentResolver, textView);
                break;
            }
        }
    }

    private void updateRequest(Uri requestUri, ContentResolver contentResolver, TextView textView) {
        ContentValues values = new ContentValues();
        values.put("name", "update note");
        values.put("date", new Date().toString());
        values.put("content", "update note content");
        int rowsUpdated = contentResolver.update(requestUri, values, null, null);
        textView.setText(String.format(Locale.getDefault(),"Rows updated: %d", rowsUpdated));
    }

    private void insertRequest(Uri requestUri, ContentResolver contentResolver, TextView textView) {
        ContentValues values = new ContentValues();
        values.put("name", "new note");
        values.put("date", new Date().toString());
        values.put("content", "new note content");
        Uri insertUri = contentResolver.insert(requestUri, values);
        if (insertUri != null) {
            textView.setText(String.format(Locale.getDefault(),"Row inserted: %s", insertUri.toString()));
        }
    }

    private void deleteRequest(Uri requestUri, ContentResolver contentResolver, TextView textView) {
        int rowsDeleted = contentResolver.delete(requestUri, null, null);
        textView.setText(String.format(Locale.getDefault(),"Rows deleted: %d", rowsDeleted));
    }

    private void selectRequest(Uri requestUri, ContentResolver contentResolver, TextView textView) {
        String[] projection = {"_id", "name", "date", "content"};
        Cursor cursor = contentResolver.query(requestUri, projection, null, null, null);
        StringBuilder data = new StringBuilder();
        if (cursor != null) {
            while (cursor.moveToNext()) {
                data.append("id = ").append(projectToUse == 1 ? cursor.getInt(cursor.getColumnIndex("_id")) :
                        cursor.getLong(cursor.getColumnIndex("id")));
                data.append(", name = ").append(cursor.getString(cursor.getColumnIndex("name")));
                data.append(", date = ").append(cursor.getString(cursor.getColumnIndex("date")));
                data.append(", content = ").append(cursor.getString(cursor.getColumnIndex("content")));
                data.append("\n");
            }
            cursor.close();
        }
        textView.setText(data.toString());
    }

    private Uri getRequestUri() {
        Uri uri = projectToUse == 1 ? Uri.parse(URL_1) : Uri.parse(URL_2);
        if (isUseId) {
            String id = editText.getText().toString();
            if (!id.equals("")) {
                uri = Uri.withAppendedPath(uri, id);
            }
        }
        return uri;
    }
}
