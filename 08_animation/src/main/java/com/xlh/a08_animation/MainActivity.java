package com.xlh.a08_animation;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    CircleView view;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        view =findViewById(R.id.view);


        ObjectAnimator animator = ObjectAnimator.ofFloat(view,"radius",Utils.dpToPixel(150));
        animator.setStartDelay(1000);
        animator.start();

    }
}
