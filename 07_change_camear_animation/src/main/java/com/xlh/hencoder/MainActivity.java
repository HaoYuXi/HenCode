package com.xlh.hencoder;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;

import com.xlh.hencoder.view.CameraView;

public class MainActivity extends AppCompatActivity {

    CameraView view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        view = findViewById(R.id.view);

        ObjectAnimator dxAnimator = ObjectAnimator.ofFloat(view,"dx",-(100+400/2));
        dxAnimator.setStartDelay(1000);
        dxAnimator.setDuration(1500);
        dxAnimator.start();
//        ObjectAnimator dyAnimator = ObjectAnimator.ofFloat(view,"dy",-(100+400/2));

        AnimatorSet animatorSet = new AnimatorSet();
//        animatorSet.playSequentially(dxAnimator,dyAnimator);
        animatorSet.setStartDelay(1000);
        animatorSet.setDuration(1500);
//        animatorSet.start();


    }
}
