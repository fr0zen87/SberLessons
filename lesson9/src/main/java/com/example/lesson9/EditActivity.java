package com.example.lesson9;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.example.lesson9.databases.DbHelper;
import com.example.lesson9.entities.MyNote;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static com.example.lesson9.databases.NoteContract.Note;

public class EditActivity extends AppCompatActivity {

    private EditText nameEditView;
    private EditText contentEditView;
    private MyNote myNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            myNote = bundle.getParcelable(MainActivity.MY_NOTE);
        }

        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment viewFragment = fragmentManager.findFragmentById(R.id.fr_view);
        Fragment editFragment = fragmentManager.findFragmentById(R.id.fr_edit);

        viewFragment.setArguments(bundle);
        editFragment.setArguments(bundle);

        nameEditView = editFragment.getActivity().findViewById(R.id.fr_name_edit);
        contentEditView = editFragment.getActivity().findViewById(R.id.fr_content_edit);
    }

    public void saveNoteClick(final View view) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                SimpleDateFormat format = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss", Locale.getDefault());
                DbHelper dbHelper = null;
                SQLiteDatabase db = null;
                try {
                    dbHelper = new DbHelper(getBaseContext());
                    db = dbHelper.getWritableDatabase();

                    db.beginTransaction();
                    final String name = nameEditView.getText().toString();
                    final String date = format.format(new Date());
                    final String content = contentEditView.getText().toString();
                    ContentValues contentValues = new ContentValues();
                    contentValues.put(Note.COLUMN_NAME, name);
                    contentValues.put(Note.COLUMN_DATE, date);
                    contentValues.put(Note.COLUMN_CONTENT, content);
                    final long id = db.update(Note.TABLE_NAME,
                            contentValues,
                            Note._ID + " = ?",
                            new String[]{String.valueOf(myNote.getId())});
                    db.setTransactionSuccessful();

                    view.post(new Runnable() {
                        @Override
                        public void run() {
                            if (id == 1) {
                                myNote.setName(name);
                                myNote.setDate(date);
                                myNote.setContent(content);
                            }

                            Message message = Message.obtain();
                            message.what = MainActivity.OTHER_NOTE;
                            Bundle bundle = new Bundle();
                            bundle.putParcelable(MainActivity.MY_NOTE, myNote);
                            message.setData(bundle);
                            MainActivity.handler.sendMessage(message);

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
    }
}
