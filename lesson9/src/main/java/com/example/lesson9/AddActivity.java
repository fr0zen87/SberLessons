package com.example.lesson9;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.example.lesson9.databases.DbHelper;
import com.example.lesson9.entities.MyNote;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static com.example.lesson9.databases.NoteContract.Note;

public class AddActivity extends AppCompatActivity {

    private EditText nameEditText;
    private EditText contentEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        nameEditText = findViewById(R.id.name_edit);
        contentEditText = findViewById(R.id.content_edit);
    }

    public void addNote(final View view) {
        SimpleDateFormat format = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss", Locale.getDefault());
        final String nameText = nameEditText.getText().toString();
        final String contentText = contentEditText.getText().toString();
        final String date = format.format(new Date());
        new Thread(new Runnable() {
            @Override
            public void run() {
                DbHelper dbHelper = null;
                SQLiteDatabase db = null;
                try {
                    dbHelper = new DbHelper(view.getContext());
                    db = dbHelper.getWritableDatabase();

                    db.beginTransaction();
                    ContentValues contentValues = new ContentValues();
                    contentValues.put(Note.COLUMN_NAME, nameText);
                    contentValues.put(Note.COLUMN_DATE, date);
                    contentValues.put(Note.COLUMN_CONTENT, contentText);
                    final long id = db.insert(Note.TABLE_NAME, null, contentValues);
                    db.setTransactionSuccessful();

                    view.post(new Runnable() {
                        @Override
                        public void run() {
                            MyNote myNote = new MyNote((int) id, nameText, date, contentText);
                            getIntent().putExtra(MainActivity.MY_NOTE, myNote);
                            setResult(RESULT_OK);
                            finish();
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (db != null) {
                        if (db.inTransaction()) {
                            db.endTransaction();
                        }
                        db.close();
                    }
                }
            }
        }).start();
        view.findViewById(R.id.progressBar).setVisibility(View.VISIBLE);
    }
}
