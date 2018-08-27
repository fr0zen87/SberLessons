package com.example.lesson25;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_READ_EXTERNAL_STORAGE = 1;
    private static boolean READ_EXTERNAL_STORAGE_GRANTED = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 5);
        RecyclerView recyclerView = findViewById(R.id.rv_images);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);

        int hasReadContactPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
        if(hasReadContactPermission == PackageManager.PERMISSION_GRANTED) {
            READ_EXTERNAL_STORAGE_GRANTED = true;
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_CODE_READ_EXTERNAL_STORAGE);
        }

        if (READ_EXTERNAL_STORAGE_GRANTED) {
            String[] projection = {MediaStore.Images.Media.DATA};
            Cursor cursor = null;
            List<String> adapterList = new ArrayList<>();

            try {
                cursor = getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, projection, null, null, null);
                if (cursor != null) {
                    while (cursor.moveToNext()) {
                        int index = cursor.getColumnIndex(MediaStore.Images.Media.DATA);
                        String uri = cursor.getString(index);
                        adapterList.add(uri);
                    }
                }
            } finally {
                if (cursor != null) {
                    cursor.close();
                }
            }

            ImageGalleryAdapter adapter = new ImageGalleryAdapter(this, adapterList);
            recyclerView.setAdapter(adapter);
        } else {
            Toast.makeText(this, "Требуется установить разрешения", Toast.LENGTH_LONG).show();
        }
    }
}
