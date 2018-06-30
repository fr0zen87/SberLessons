package com.example.lesson9;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;

import com.example.lesson9.adapters.MyNotesAdapter;
import com.example.lesson9.databases.DbHelper;
import com.example.lesson9.entities.MyNote;

import java.util.ArrayList;
import java.util.List;

import static com.example.lesson9.databases.NoteContract.Note;

public class MainActivity extends AppCompatActivity {

    public static final int ADD_REQUEST_CODE = 1;
    public static final int EDIT_REQUEST_CODE = 2;
    public static final int INIT_NOTES = 1001;
    public static final int ADD_NOTE = 1002;
    public static final int EDIT_NOTE = 1003;
    public static final String MY_NOTE = "myNote";

    private List<MyNote> notes = new ArrayList<>();
    private MyNotesAdapter adapter;

    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initRecyclerView();
        initNotes();

        handler = new MyHandler();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
        return true;
    }

    @Override
    protected void onActivityResult(final int requestCode, int resultCode, final Intent data) {
        if (resultCode == RESULT_OK && data != null) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    Message message = new Message();
                    Bundle bundle = new Bundle();
                    bundle.putParcelable(MY_NOTE, data.getParcelableExtra(MY_NOTE));
                    message.setData(bundle);
                    switch (requestCode) {
                        case ADD_REQUEST_CODE: {
                            message.what = ADD_NOTE;
                            break;
                        }
                        case EDIT_REQUEST_CODE: {
                            message.what = EDIT_NOTE;
                            break;
                        }
                    }
                    handler.sendMessage(message);
                }
            }).start();
        }
    }

    public void addNoteClick(View view) {
        Intent intent = new Intent(this, AddActivity.class);
        startActivityForResult(intent, ADD_REQUEST_CODE);
    }

    private void initRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.list);
        adapter = new MyNotesAdapter(notes);
        recyclerView.setAdapter(adapter);
        recyclerView.addOnItemTouchListener(listener);
        DividerItemDecoration dividerItemDecoration= new DividerItemDecoration(this,
                LinearLayoutManager.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);
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
                    if (cursor.moveToFirst()) {
                        do {
                            int id = cursor.getInt(cursor.getColumnIndex(Note._ID));
                            String name = cursor.getString(cursor.getColumnIndex(Note.COLUMN_NAME));
                            String date = cursor.getString(cursor.getColumnIndex(Note.COLUMN_DATE));
                            String content = cursor.getString(cursor.getColumnIndex(Note.COLUMN_CONTENT));
                            MyNote myNote = new MyNote(id, name, date, content);
                            Message message = new Message();
                            Bundle bundle = new Bundle();
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

    @SuppressLint("HandlerLeak")
    private class MyHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case INIT_NOTES: {
                    Bundle bundle = msg.getData();
                    MyNote myNote = bundle.getParcelable(MY_NOTE);
                    notes.add(myNote);
                    adapter.notifyDataSetChanged();
                    break;
                }
                case ADD_NOTE: {
                    Bundle bundle = msg.getData();
                    MyNote myNote = bundle.getParcelable(MY_NOTE);
                    if (myNote != null) {
                        notes.add(myNote);
                        adapter.notifyItemChanged(myNote.getId());
                    }
                    break;
                }
                case EDIT_NOTE: {
                    Bundle bundle = msg.getData();
                    MyNote myNote = bundle.getParcelable(MY_NOTE);
                    if (myNote != null) {
                        notes.set(myNote.getId() - 1, myNote);
                        adapter.notifyItemChanged(myNote.getId() - 1);
                    }
                    break;
                }
            }
        }
    }

    private RecyclerView.OnItemTouchListener listener = new RecyclerView.OnItemTouchListener() {

        GestureDetector gestureDetector = new GestureDetector(getBaseContext(), new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onSingleTapUp(MotionEvent motionEvent) {
                return true;
            }
        });

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent event) {
            View view = rv.findChildViewUnder(event.getX(), event.getY());

            if (view != null && gestureDetector.onTouchEvent(event)) {
                int pos = rv.getChildAdapterPosition(view);
                MyNote note = adapter.getNotes().get(pos);
                Intent intent = new Intent(getBaseContext(), EditActivity.class)
                        .putExtra(MY_NOTE, note);
                startActivityForResult(intent, EDIT_REQUEST_CODE);
            }
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {

        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }
    };
}
