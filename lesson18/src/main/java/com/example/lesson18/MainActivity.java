package com.example.lesson18;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;

import com.example.lesson18.entities.Image;
import com.example.lesson18.retrofit.RetrofitHelper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RetrofitHelper retrofitHelper = new RetrofitHelper();
        List<String> uris = new ArrayList<>();
        try {
            Response<Image> imageResponse = retrofitHelper.getService().getImages().execute();
            Image image = imageResponse.body();
            List<Image.Images> images = image.getImages();
            for (Image.Images img : images) {
                String uri = img.getDisplaySizes().get(0).getUri();
                uris.add(uri);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        MyListAdapter adapter = new MyListAdapter(uris);
        recyclerView.setAdapter(adapter);
    }
}
