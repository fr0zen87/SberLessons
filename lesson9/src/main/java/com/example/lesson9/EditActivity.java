package com.example.lesson9;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.example.lesson9.databases.DbHelper;
import com.example.lesson9.entities.MyNote;
import com.example.lesson9.fragments.EditFragment;
import com.example.lesson9.fragments.ViewFragment;

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
        myNote = bundle.getParcelable(MainActivity.MY_NOTE);

        ViewFragment viewFragment = new ViewFragment();
        viewFragment.setArguments(bundle);
        EditFragment editFragment = new EditFragment();

        getSupportFragmentManager().beginTransaction()
                .add(R.id.view_fragment_container, viewFragment)
                .add(R.id.edit_fragment_container, editFragment)
                .commit();

        nameEditView = editFragment.getView().findViewById(R.id.fr_name_edit);
        contentEditView = editFragment.getView().findViewById(R.id.fr_content_edit);

        nameEditView.setText(myNote.getName());
        contentEditView.setText(myNote.getContent());
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
                    ContentValues contentValues = new ContentValues();
                    contentValues.put(Note.COLUMN_NAME, nameEditView.getText().toString());
                    contentValues.put(Note.COLUMN_DATE, format.format(new Date()));
                    contentValues.put(Note.COLUMN_CONTENT, contentEditView.getText().toString());
                    db.update(Note.TABLE_NAME,
                            contentValues,
                            Note._ID + " = ?",
                            new String[]{String.valueOf(myNote.getId())});
                    db.setTransactionSuccessful();

                    view.post(new Runnable() {
                        @Override
                        public void run() {
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
    }
}
