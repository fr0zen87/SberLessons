package com.example.lesson22;

import android.graphics.Color;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    String pattern = "Coordinates: x = %.0f, y = %.0f";

    TextView textView;
    RectView rectView;
    Rect rect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.textView);
        rectView = findViewById(R.id.rectView);
        rect = rectView.rect;

        rectView.setOnTouchListener(new Listener());
    }

    class Listener implements View.OnTouchListener {

        @Override
        public boolean onTouch(View view, MotionEvent event) {
            float x = view.getX();
            float y = view.getY();
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN: {
                    int color = Color.rgb(new Random().nextInt(256),
                            new Random().nextInt(256),
                            new Random().nextInt(256));
                    rectView.paint.setColor(color);
                    break;
                }
                case MotionEvent.ACTION_MOVE: {
                    float dx = x + event.getX();
                    float dy = y + event.getY();
                    rectView.setX(dx);
                    rectView.setY(dy);
                    textView.setText(String.format(pattern, dx, dy));
                    break;
                }
            }
            rectView.performClick();
            rectView.invalidate();
            return true;
        }
    }
}
