package com.example.lesson22;

import android.graphics.Color;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.textView);
        rectView = findViewById(R.id.rectView);

        rectView.setOnTouchListener(new Listener());
    }

    class Listener implements View.OnTouchListener {
        float x;
        float y;

        @Override
        public boolean onTouch(View view, MotionEvent event) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN: {
                    int color = Color.rgb(new Random().nextInt(256),
                            new Random().nextInt(256),
                            new Random().nextInt(256));
                    rectView.paint.setColor(color);
                    x = view.getX() - event.getRawX();
                    y = view.getY() - event.getRawY();
                    break;
                }
                case MotionEvent.ACTION_MOVE: {
                    float dx = event.getRawX() + x;
                    float dy = event.getRawY() + y;
                    view.animate()
                            .x(dx)
                            .y(dy)
                            .setDuration(0)
                            .start();
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
