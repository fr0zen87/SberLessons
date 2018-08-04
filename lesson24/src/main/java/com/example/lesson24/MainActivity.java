package com.example.lesson24;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.transition.TransitionManager;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.BounceInterpolator;
import android.view.animation.CycleInterpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.OvershootInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    //drawable animation
    ImageView weatherImageView;

    //base animation
    TextView baseAnimTextViewXml;
    TextView baseAnimTextViewCode;

    ImageView valueAnimatorImageView1;
    ImageView valueAnimatorImageView2;
    ImageView valueAnimatorImageView3;
    ImageView valueAnimatorImageView4;
    ImageView valueAnimatorImageView5;

    //object animator
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //drawable animation
        weatherImageView = findViewById(R.id.weatherImageView);
        weatherImageView.setBackgroundResource(R.drawable.weather_animation);
        AnimationDrawable animationDrawable = (AnimationDrawable) weatherImageView.getBackground();
        animationDrawable.start();

        //base animation from xml
        baseAnimTextViewXml = findViewById(R.id.baseAnimTextViewXml);
        Animation baseAnimationFromXml = AnimationUtils.loadAnimation(this, R.anim.base_anim);
        baseAnimTextViewXml.startAnimation(baseAnimationFromXml);

        //base animation from code
        baseAnimTextViewCode = findViewById(R.id.baseAnimTextViewCode);
        Animation baseAnimationFromCode = new RotateAnimation(0,
                360,
                Animation.RELATIVE_TO_SELF,
                0.5f,
                Animation.RELATIVE_TO_SELF,
                0.5f);
        baseAnimationFromCode.setDuration(800);
        baseAnimationFromCode.setRepeatCount(ValueAnimator.INFINITE);
        baseAnimTextViewCode.startAnimation(baseAnimationFromCode);

        //value animator
        valueAnimatorImageView1 = findViewById(R.id.valueAnimatorImageView1);
        valueAnimatorImageView2 = findViewById(R.id.valueAnimatorImageView2);
        valueAnimatorImageView3 = findViewById(R.id.valueAnimatorImageView3);
        valueAnimatorImageView4 = findViewById(R.id.valueAnimatorImageView4);
        valueAnimatorImageView5 = findViewById(R.id.valueAnimatorImageView5);

        ValueAnimator valueAnimator1 = ValueAnimator.ofInt(20, 100);
        valueAnimator1.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int value = (int) animation.getAnimatedValue();

                valueAnimatorImageView1.getLayoutParams().width = value;
                valueAnimatorImageView1.getLayoutParams().height = value;
                valueAnimatorImageView1.requestLayout();
            }
        });
        valueAnimator1.setInterpolator(new LinearInterpolator());
        valueAnimator1.setDuration(1500);
        valueAnimator1.setRepeatCount(ValueAnimator.INFINITE);
        valueAnimator1.setRepeatMode(ValueAnimator.REVERSE);
        valueAnimator1.start();

        valueAnimatorImageView1 = findViewById(R.id.valueAnimatorImageView1);
        valueAnimatorImageView2 = findViewById(R.id.valueAnimatorImageView2);
        valueAnimatorImageView3 = findViewById(R.id.valueAnimatorImageView3);
        valueAnimatorImageView4 = findViewById(R.id.valueAnimatorImageView4);
        valueAnimatorImageView5 = findViewById(R.id.valueAnimatorImageView5);

        ValueAnimator valueAnimator2 = ValueAnimator.ofFloat(0, 360);
        valueAnimator2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {

                valueAnimatorImageView2.setRotation((Float) animation.getAnimatedValue());
            }
        });
        valueAnimator2.setInterpolator(new BounceInterpolator());
        valueAnimator2.setDuration(1500);
        valueAnimator2.setRepeatCount(ValueAnimator.INFINITE);
        valueAnimator2.start();

        ValueAnimator valueAnimator3 = ValueAnimator.ofFloat(0, 1000);
        valueAnimator3.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {

                valueAnimatorImageView3.setTranslationX((Float) animation.getAnimatedValue());
            }
        });
        valueAnimator3.setInterpolator(new AccelerateInterpolator());
        valueAnimator3.setDuration(1500);
        valueAnimator3.setRepeatCount(ValueAnimator.INFINITE);
        valueAnimator3.setRepeatMode(ValueAnimator.REVERSE);
        valueAnimator3.start();

        ValueAnimator valueAnimator4 = ValueAnimator.ofFloat(0, 500);
        valueAnimator4.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {

                valueAnimatorImageView4.setTranslationY((Float) animation.getAnimatedValue());
            }
        });
        valueAnimator4.setInterpolator(new OvershootInterpolator());
        valueAnimator4.setDuration(1000);
        valueAnimator4.setRepeatCount(ValueAnimator.INFINITE);
        valueAnimator4.setRepeatMode(ValueAnimator.REVERSE);
        valueAnimator4.start();

        ValueAnimator valueAnimator5 = ValueAnimator.ofFloat(1f, 3f);
        valueAnimator5.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();

                valueAnimatorImageView5.setScaleX(value);
                valueAnimatorImageView5.setScaleY(value);
            }
        });
        valueAnimator5.setInterpolator(new CycleInterpolator(2));
        valueAnimator5.setDuration(2000);
        valueAnimator5.setRepeatCount(ValueAnimator.INFINITE);
        valueAnimator5.start();

        //object animator
        button = findViewById(R.id.button);
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(button, "translationY", 0f, -800f);
        objectAnimator.setDuration(1500);
        objectAnimator.setRepeatMode(ValueAnimator.REVERSE);
        objectAnimator.setInterpolator(new BounceInterpolator());
        objectAnimator.setRepeatCount(ValueAnimator.INFINITE);
        objectAnimator.start();

        //transition
        final ViewGroup sceneRoot = findViewById(R.id.container);
        final View sun = sceneRoot.findViewById(R.id.sunImageView);

        sun.setOnClickListener(new View.OnClickListener() {
            boolean dogeSize = true;

            @Override
            public void onClick(View v) {
                TransitionManager.beginDelayedTransition(sceneRoot);
                ViewGroup.LayoutParams params = sun.getLayoutParams();

                if (dogeSize) {
                    params.width = 300;
                    params.height = 300;
                    sun.setLayoutParams(params);
                    dogeSize = false;
                } else {
                    params.width = 150;
                    params.height = 150;
                    sun.setLayoutParams(params);
                    dogeSize = true;
                }
            }
        });
    }
}
