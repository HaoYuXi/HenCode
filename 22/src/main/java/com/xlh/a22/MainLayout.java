package com.xlh.a22;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintLayout;


/**
 * Date: 20-6-17
 * Time: 下午1:13
 * Author: dain
 */
class MainLayout extends ConstraintLayout implements MainActivity.IView {

    public EditText editText1;
    public EditText editText2;
    public TextView text;
    public Activity activity;



    public MainLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        editText1 = findViewById(R.id.editText1);
        editText2 = findViewById(R.id.editText2);
        text = findViewById(R.id.textView2);

        text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "提交数据", Toast.LENGTH_SHORT).show();
                editText1.setText("Hello World");
                activity.finish();

            }
        });


    }

    @Override
    public void showData(String[] content){
        editText1.setText(content[0]);
        editText2.setText(content[1]);
    }

    @Override
    public void activity(Activity activity) {
        this.activity = activity;

    }


}
