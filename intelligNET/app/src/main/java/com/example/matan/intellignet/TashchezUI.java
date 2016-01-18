package com.example.matan.intellignet;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;

import java.util.ArrayList;

public class TashchezUI extends AppCompatActivity
{
    private final int NUM_COL=7;
    private final int NUM_ROW=7;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tashchez_ui);

        TashchezAdapter adapter;
        GridView menuGrid = (GridView)findViewById(R.id.menuGrid);
        TypeTashchezGrid tashchez = new TypeTashchezGrid(NUM_ROW, NUM_COL, true, "data from DB");
        menuGrid.setNumColumns(NUM_COL);


        for(int i=0 ; i < tashchez.numColumns ; i++)
        {
            adapter = new TashchezAdapter(this, R.layout.cell_tashchez, tashchez.board);
            menuGrid.setAdapter(adapter);
        }


        startService(new Intent(this, ChatHeadService.class));

        FloatingActionButton chatHead = (FloatingActionButton)findViewById(R.id.fab);
        chatHead.setOnTouchListener(new View.OnTouchListener() {
            private float initialX;
            private float initialY;
            private float initialTouchX;
            private float initialTouchY;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        initialX = v.getX();
                        initialY = v.getY();
                        initialTouchX = event.getRawX();
                        initialTouchY = event.getRawY();
                        return true;
                    case MotionEvent.ACTION_UP:
                        return true;
                    case MotionEvent.ACTION_MOVE:
                        v.setX(initialX + (int) (event.getRawX() - initialTouchX));
                        v.setY(initialY + (int) (event.getRawY() - initialTouchY));
                        return true;
                }
                return false;
            }
        });




    }
}
