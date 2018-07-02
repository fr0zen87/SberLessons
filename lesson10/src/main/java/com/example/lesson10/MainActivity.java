package com.example.lesson10;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.lesson10.entities.MyNote;
import com.example.lesson10.utils.MyNoteViewModel;
import com.example.lesson10.utils.MyNotesAdapter;

import java.util.List;

public class MainActivity extends AppCompatActivity implements MyNotesAdapter.MyCallback {

    public static final int ADD_REQUEST_CODE = 1;
    public static final int EDIT_REQUEST_CODE = 2;
    public static final String MY_NOTE = "myNote";

    private MyNoteViewModel mMyNoteViewModel;
    private MyNotesAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddActivity.class);
                startActivityForResult(intent, ADD_REQUEST_CODE);
            }
        });

        initRecyclerView();

        mMyNoteViewModel = ViewModelProviders.of(this).get(MyNoteViewModel.class);
        mMyNoteViewModel.getAllNotes().observe(this, new Observer<List<MyNote>>() {
            @Override
            public void onChanged(@Nullable final List<MyNote> notes) {
                adapter.setNotes(notes);
            }
        });
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && data != null) {
            MyNote myNote = data.getParcelableExtra(MY_NOTE);
            if (requestCode == ADD_REQUEST_CODE) {
                mMyNoteViewModel.insert(myNote);
            } else if (requestCode == EDIT_REQUEST_CODE) {
                mMyNoteViewModel.update(myNote);
            }
        } else if (requestCode == RESULT_CANCELED) {
            Toast.makeText(
                    getApplicationContext(),
                    R.string.empty_not_saved,
                    Toast.LENGTH_LONG).show();
        }
    }

    private void initRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.list);
        adapter = new MyNotesAdapter();
        adapter.registerCallback(this);
        recyclerView.setAdapter(adapter);
        //recyclerView.addOnItemTouchListener(listener);
        DividerItemDecoration dividerItemDecoration= new DividerItemDecoration(this,
                LinearLayoutManager.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);
    }

//    private RecyclerView.OnItemTouchListener listener = new RecyclerView.OnItemTouchListener() {
//
//        GestureDetector gestureDetector = new GestureDetector(getBaseContext(), new GestureDetector.SimpleOnGestureListener() {
//            @Override
//            public boolean onSingleTapUp(MotionEvent motionEvent) {
//                return true;
//            }
//
//            @Override
//            public void onLongPress(MotionEvent motionEvent) {
//                super.onLongPress(motionEvent);
//            }
//        });
//
//        @Override
//        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent event) {
//            View view = rv.findChildViewUnder(event.getX(), event.getY());
//
//            if (view != null && gestureDetector.onTouchEvent(event)) {
//                int pos = rv.getChildAdapterPosition(view);
//                MyNote note = adapter.getNotes().get(pos);
//                Intent intent = new Intent(getBaseContext(), EditActivity.class)
//                        .putExtra(MY_NOTE, note);
//                startActivityForResult(intent, EDIT_REQUEST_CODE);
//            }
//            return false;
//        }
//
//        @Override
//        public void onTouchEvent(RecyclerView rv, MotionEvent e) {
//
//        }
//
//        @Override
//        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
//
//        }
//    };

    @Override
    public void onEditClick(MyNote myNote) {
        Intent intent = new Intent(getBaseContext(), EditActivity.class)
                .putExtra(MY_NOTE, myNote);
        startActivityForResult(intent, EDIT_REQUEST_CODE);
    }

    @Override
    public void onDeleteClick(MyNote myNote) {
        mMyNoteViewModel.delete(myNote);
    }
}
