package com.example.matan.intellignet;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.GridView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        GridView menuGrid = (GridView)findViewById(R.id.menuGrid);

        menuGrid.setVerticalScrollBarEnabled(false);
    }
}
