package com.xlh.a22;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    IView mainLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainLayout = findViewById(R.id.main_layout);

        mainLayout.showData(MainData.getData());
        mainLayout.activity(this);
        

    }

    public interface IView{
        void showData(String[] content);
        void activity(Activity activity);
    }
}
