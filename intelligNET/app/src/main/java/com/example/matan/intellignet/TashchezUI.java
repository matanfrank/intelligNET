package com.example.matan.intellignet;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.logging.Handler;

public class TashchezUI extends AppCompatActivity
{
    public static final int NUM_COL=7;
    public static final int NUM_ROW=7;
    private  ImageView coverImage;
    private InputMethodManager imm;
    private static EditText editText;
    private  GridView tashchezGrid;
    private  TypeTashchezGrid tashchez;
    public static boolean solveMode = false;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tashchez_ui);

        tashchezGrid = (GridView)findViewById(R.id.menuGrid);
        tashchez = new TypeTashchezGrid(NUM_ROW, NUM_COL, true, "data from DB");
        tashchezGrid.setNumColumns(NUM_COL);
        coverImage = (ImageView) findViewById(R.id.symbolImage);

        TashchezAdapter adapter = new TashchezAdapter(this, R.layout.cell_definition_tashchez, R.layout.cell_solve_tashchez, tashchez.board, new TashchezPassEditText() {
            @Override
            public void setEditText(EditText editText) {
                TashchezUI.editText = editText;
            }
        });


        tashchezGrid.setAdapter(adapter);

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




    //get out of SOLVE_MODE and if is out of SOLVE_MODE exit the activity
    @Override
    public boolean dispatchKeyEvent(KeyEvent event)
    {
            if (event.getKeyCode() == KeyEvent.KEYCODE_BACK)
            {
                android.os.Handler h = new android.os.Handler();

                Runnable r = new Runnable() {
                    @Override
                    public void run() {
                      //  TashchezUI.editText.clearFocus();
                        coverImage.setVisibility(View.VISIBLE);
                    }
                };
                h.postDelayed(r, 300);
//                solveMode = false;
            }
            return solveMode;
    }
}


