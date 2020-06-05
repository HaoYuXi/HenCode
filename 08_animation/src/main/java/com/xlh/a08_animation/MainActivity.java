package com.xlh.a08_animation;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    CameraView view;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        view = findViewById(R.id.view);


//        ObjectAnimator animator = ObjectAnimator.ofFloat(view,"radius",Utils.dpToPixel(150));
//        ObjectAnimator animator2 = ObjectAnimator.ofFloat(view,"radius",Utils.dpToPixel(50));
//        ObjectAnimator animator3 = ObjectAnimator.ofFloat(view,"radius",Utils.dpToPixel(120));
//        ObjectAnimator animator4 = ObjectAnimator.ofFloat(view,"radius",Utils.dpToPixel(70));
//        AnimatorSet animatorSet = new AnimatorSet();
//        animatorSet.playSequentially(animator,animator2,animator3,animator4);
//        animatorSet.setStartDelay(1000);
//        animatorSet.setDuration(1500);
//        animatorSet.start();

        //旋转回复成初始样子  角度初始值 top 0,bottom 45,rotation 0
        /*ObjectAnimator topFlip =        ObjectAnimator.ofFloat(view,"topFlip",180);
        ObjectAnimator bottomFlip =     ObjectAnimator.ofFloat(view,"bottomFlip",180);
        ObjectAnimator flipRotation =    ObjectAnimator.ofFloat(view,"flipRotation",90);
        ObjectAnimator flipRotation2 =    ObjectAnimator.ofFloat(view,"bottomFlip",0);
        ObjectAnimator topFlip2 =        ObjectAnimator.ofFloat(view,"topFlip",0);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playSequentially(topFlip,bottomFlip,flipRotation,flipRotation2,topFlip2);
        animatorSet.setStartDelay(1000);
        animatorSet.setDuration(1500);
        animatorSet.start();*/

//        ObjectAnimator topFlip = ObjectAnimator.ofFloat(view, "topFlip", 25);
        ObjectAnimator bottomFlip = ObjectAnimator.ofFloat(view, "bottomFlip", 45);
        ObjectAnimator flipRotation = ObjectAnimator.ofFloat(view, "flipRotation", 360);
        ObjectAnimator bottomFlip2 = ObjectAnimator.ofFloat(view, "bottomFlip", 0);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playSequentially( bottomFlip, flipRotation, bottomFlip2);
//        animatorSet.playSequentially(topFlip, bottomFlip, flipRotation, bottomFlip2);
        animatorSet.setStartDelay(1000);
        animatorSet.setDuration(1500);
        animatorSet.start();


    }
}
