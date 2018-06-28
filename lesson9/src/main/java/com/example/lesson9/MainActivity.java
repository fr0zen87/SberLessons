package com.example.lesson9;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.lesson9.adapters.MyNotesAdapter;
import com.example.lesson9.databases.DbHelper;
import com.example.lesson9.entities.MyNote;

import java.util.ArrayList;
import java.util.List;

import static com.example.lesson9.databases.NoteContract.Note;

public class MainActivity extends AppCompatActivity {

    public static final int REQUEST_CODE = 1;
    public static final int INIT_NOTES = 1001;
    public static final int OTHER_NOTE = 1002;
    public static final String MY_NOTE = "myNote";

    private List<MyNote> notes = new ArrayList<>();

    private RecyclerView.Adapter adapter;
    private Handler handler;

    @SuppressLint("HandlerLeak")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.list);
        adapter = new MyNotesAdapter(notes);
        recyclerView.setAdapter(adapter);

        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                Bundle bundle = msg.getData();
                MyNote myNote = bundle.getParcelable(MY_NOTE);
                notes.add(myNote);
                switch (msg.what) {
                    case INIT_NOTES: {
                        adapter.notifyDataSetChanged();
                        break;
                    }
                    case OTHER_NOTE: {
                        adapter.notifyItemChanged(myNote.getId());
                        break;
                    }
                }
            }
        };

        initNotes();
    }

    public void addNoteClick(View view) {
        Intent intent = new Intent(this, AddActivity.class);
        startActivityForResult(intent, REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, final Intent data) {
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            if (data != null) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Message message = Message.obtain();
                        Bundle bundle = new Bundle();
                        bundle.putParcelable(MY_NOTE, data.getParcelableExtra(MY_NOTE));
                        message.setData(bundle);
                        message.what = OTHER_NOTE;
                        handler.sendMessage(message);
                    }
                }).start();
            }
        }
    }

    private void initNotes() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                DbHelper dbHelper = null;
                SQLiteDatabase db = null;
                Cursor cursor = null;
                try {
                    dbHelper = new DbHelper(getBaseContext());
                    db = dbHelper.getReadableDatabase();
                    cursor = db.query(Note.TABLE_NAME, null, null, null, null, null, null);
                    Message message = Message.obtain();
                    Bundle bundle = new Bundle();
                    MyNote myNote = null;
                    if (cursor.moveToFirst()) {
                        do {
                            int id = cursor.getInt(cursor.getColumnIndex(Note._ID));
                            String name = cursor.getString(cursor.getColumnIndex(Note.COLUMN_NAME));
                            String date = cursor.getString(cursor.getColumnIndex(Note.COLUMN_DATE));
                            String content = cursor.getString(cursor.getColumnIndex(Note.COLUMN_CONTENT));
                            myNote = new MyNote(id, name, date, content);
                            bundle.putParcelable(MY_NOTE, myNote);
                            message.setData(bundle);
                            message.what = INIT_NOTES;
                            handler.sendMessage(message);
                        } while (cursor.moveToNext());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (db != null) {
                        if (cursor != null) {
                            cursor.close();
                        }
                        db.close();
                    }
                }
            }
        }).start();
    }
}
