package com.example.lesson9;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.ContentObserver;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
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
import com.example.lesson9.db.DbHelper;
import com.example.lesson9.entities.MyNote;
import com.example.lesson9.provider.NotesContract;

import java.util.ArrayList;
import java.util.List;

import static com.example.lesson9.db.NoteContract.Note;

public class MainActivity extends AppCompatActivity {

    public static final int ADD_REQUEST_CODE = 1;
    public static final int EDIT_REQUEST_CODE = 2;
    public static final int INIT_NOTES = 1001;
    public static final String MY_NOTE = "myNote";

    private List<MyNote> notes = new ArrayList<>();
    private RecyclerView recyclerView;
    private MyNotesAdapter adapter;

    private Handler handler;
    private MyObserver myObserver;
    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initRecyclerView();
        initNotes();

        handler = new MyHandler();
        myObserver = new MyObserver(new Handler(Looper.getMainLooper()));
    }

    @Override
    protected void onResume() {
        super.onResume();
        adapter.notifyDataSetChanged();
        getContentResolver().registerContentObserver(NotesContract.CONTENT_URI, true, myObserver);
    }

    @Override
    protected void onPause() {
        super.onPause();
        getContentResolver().unregisterContentObserver(myObserver);
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
            switch (requestCode) {
                case ADD_REQUEST_CODE: {
                    MyNote myNote = data.getParcelableExtra(MY_NOTE);
                    notes.add(myNote);
                    adapter.notifyDataSetChanged();
                    break;
                }
                case EDIT_REQUEST_CODE: {
                    MyNote myNote = data.getParcelableExtra(MY_NOTE);
                    if (myNote != null) {
                        notes.set(position, myNote);
                        adapter.notifyItemChanged(myNote.getId());
                    }
                    break;
                }
            }
        }
    }

    public void addNoteClick(View view) {
        Intent intent = new Intent(this, AddActivity.class);
        startActivityForResult(intent, ADD_REQUEST_CODE);
    }

    private void initRecyclerView() {
        recyclerView = findViewById(R.id.list);
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
            if (msg.what == INIT_NOTES) {
                Bundle bundle = msg.getData();
                MyNote myNote = bundle.getParcelable(MY_NOTE);
                notes.add(myNote);
                adapter.notifyDataSetChanged();
            }
        }
    }

    private RecyclerView.OnItemTouchListener listener = new RecyclerView.OnItemTouchListener() {

        GestureDetector gestureDetector = new GestureDetector(getBaseContext(), new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onSingleTapUp(MotionEvent motionEvent) {
                return true;
            }

            @Override
            public void onLongPress(MotionEvent e) {
                super.onLongPress(e);
                View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                int position = recyclerView.getChildAdapterPosition(child);
                MyNote deletedNote = notes.remove(position);
                adapter.notifyItemRemoved(position);
                deleteNote(deletedNote.getId());
            }
        });

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent event) {
            View view = rv.findChildViewUnder(event.getX(), event.getY());

            if (view != null && gestureDetector.onTouchEvent(event)) {
                position = rv.getChildAdapterPosition(view);
                MyNote note = adapter.getNotes().get(position);
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

    private void deleteNote(final int id) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                DbHelper dbHelper = null;
                SQLiteDatabase db = null;
                try {
                    dbHelper = new DbHelper(getBaseContext());
                    db = dbHelper.getWritableDatabase();
                    db.delete(Note.TABLE_NAME, Note._ID + " = ?", new String[]{String.valueOf(id)});
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (db != null) {
                        db.close();
                    }
                }
            }
        }).start();
    }

    private class MyObserver extends ContentObserver {

        public MyObserver(Handler handler) {
            super(handler);
        }

        @Override
        public void onChange(boolean selfChange) {
            super.onChange(selfChange);
            adapter.notifyDataSetChanged();
        }
    }
}
