package com.example.matan.intellignet;

import android.app.Activity;
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

import org.json.JSONArray;
import org.json.JSONException;

import java.util.logging.Handler;

public class TashchezUI extends AppCompatActivity
{
    public static final int NUM_COL=7;
    public static final int NUM_ROW=7;
    private  ImageView coverImage;
    private static TashchezAdapter adapter;
    private InputMethodManager imm;
    private static newEditText editText;
    private  GridView tashchezGrid;
    private  TypeTashchezGrid tashchez;
    public static boolean solveMode = false;
    private TashchezDAL tashchezDAL = new TashchezDAL(this);
    private TashchezBL tashchezBL;
    private Activity activity = this;
    public android.os.Handler h;
    public Runnable r1, r2;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tashchez_ui);








        coverImage = (ImageView) findViewById(R.id.symbolImage);
        tashchezGrid = (GridView)findViewById(R.id.menuGrid);
        tashchezGrid.setNumColumns(NUM_COL);


        h = new android.os.Handler();

        r1 = new Runnable() {
            @Override
            public void run() {


                try {
                    tashchezBL = new TashchezBL(TashchezDAL.jsonArray.getJSONObject(0).getString("content"), activity, h, r2);//maybe should add TashchezDAL.jsonArray.getJSONObject(0).getString()
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        };


        r2 = new Runnable() {
            @Override
            public void run() {


                for(int i=0 ; i<tashchezBL.getTashchezStruct().cellIndex.size() ; i++)
                    Log.d("123456789", ""+tashchezBL.getTashchezStruct().cellType.get(i) + " " + tashchezBL.getTashchezStruct().cellIndex.get(i) + " " + tashchezBL.getTashchezStruct().content.get(i) + " " + tashchezBL.getTashchezStruct().numOfLetter.get(i));
                tashchez = new TypeTashchezGrid(NUM_ROW, NUM_COL, true, tashchezBL.getTashchezStruct());


                adapter = new TashchezAdapter(activity, R.layout.cell_definition_tashchez, R.layout.cell_solve_tashchez, tashchez.board, new TashchezPassEditText() {
                    @Override
                    public void setEditText(newEditText editText) {
                        TashchezUI.editText = editText;
                    }
                });



                tashchezGrid.setAdapter(adapter);


            }
        };

        tashchezDAL.getDataFrom("tashchezGet", h, r1);









        startService(new Intent(this, ChatHeadService.class));

        FloatingActionButton chatHead = (FloatingActionButton) findViewById(R.id.fab);
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
            if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
                if (solveMode) {



                    android.os.Handler h = new android.os.Handler();

                    Runnable r = new Runnable() {
                        @Override
                        public void run() {
                            coverImage.setVisibility(View.VISIBLE);
                              TashchezUI.editText.clearFocus();
                        }
                    };
                    h.postDelayed(r, 300);




                    for (int i = 0; i < NUM_ROW * NUM_COL; i++)
                        tashchez.board.get(i).onEdit = false;

                    solveMode = false;
                }
                else
                {
                    finish();
                }
            }
            return solveMode;
    }
}


