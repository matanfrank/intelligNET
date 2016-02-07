package com.example.matan.intellignet;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

public class TashchezUI extends AppCompatActivity
{
    private final int NUM_COL=7;
    private final int NUM_ROW=7;
    private  ImageView coverImage;
    private InputMethodManager imm;
    private EditText editText;
    private  GridView tashchezGrid;
    private  TypeTashchezGrid tashchez;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tashchez_ui);

        TashchezAdapter adapter;
        int i;
        tashchezGrid = (GridView)findViewById(R.id.menuGrid);
        tashchez = new TypeTashchezGrid(NUM_ROW, NUM_COL, true, "data from DB");
        tashchezGrid.setNumColumns(NUM_COL);

        //editText = (EditText)findViewById(R.id.tashchez_text);

        for(i=0 ; i < tashchez.numColumns ; i++)
        {
            adapter = new TashchezAdapter(this, R.layout.cell_definition_tashchez ,R.layout.cell_solve_tashchez, tashchez.board);
             tashchezGrid.setAdapter(adapter);
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


         tashchezGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
             @Override
             public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                 Toast.makeText(getApplicationContext(),
                         "Item Clicked: " + position, Toast.LENGTH_SHORT).show();

                 coverImage = (ImageView)findViewById(R.id.symbolImage);
                 coverImage.setVisibility(View.GONE);

                 paintAnswer(position);

             }
         });




    }




    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if ((keyCode == KeyEvent.KEYCODE_BACK))
        {
            coverImage.setVisibility(View.VISIBLE);
            //imm.showSoftInput(findViewById(R.id.tashchez_text), InputMethodManager.RESULT_HIDDEN);
        }

        return false;
    }


    private void paintAnswer(int position)
    {
        int firstToPaint = position;
        EditText editText = (EditText)tashchezGrid.getChildAt(firstToPaint).findViewById(R.id.tashchez_edit_text);

        if(tashchez.board.get(position).cellType.contains("definition"))
        {
            for(int i=0 ; i<tashchez.board.get(position).answerNumOfLetter ; i++)
            {
                if(tashchez.board.get(position).cellType.contains("Down") && tashchez.board.get(position).cellType.contains("Left") && tashchez.board.get(position).cellType.endsWith("Left"))
                {
                    if(i==0)
                        firstToPaint += NUM_ROW;
                    else
                        firstToPaint += 1;
                }
                else if(tashchez.board.get(position).cellType.contains("Left") && tashchez.board.get(position).cellType.contains("Down") && tashchez.board.get(position).cellType.endsWith("Down"))
                {
                    if(i==0)
                        firstToPaint += 1;
                    else
                        firstToPaint += NUM_ROW;
                }
                else if(tashchez.board.get(position).cellType.contains("Up") && tashchez.board.get(position).cellType.contains("Left")&& tashchez.board.get(position).cellType.endsWith("Left"))
                {
                    if(i==0)
                       firstToPaint -= NUM_ROW;
                    else
                        firstToPaint += 1;
                }
                else if(tashchez.board.get(position).cellType.contains("Right") && tashchez.board.get(position).cellType.contains("Down")&& tashchez.board.get(position).cellType.endsWith("Down"))
                {
                    if(i==0)
                        firstToPaint -= 1;
                    else
                        firstToPaint += NUM_ROW;
                }
                else if(tashchez.board.get(position).cellType.contains("Left"))
                    firstToPaint += 1;
                else if(tashchez.board.get(position).cellType.contains("Down"))
                    firstToPaint += NUM_ROW;

                ImageView img = (ImageView)tashchezGrid.getChildAt(firstToPaint).findViewById(R.id.tashchez_image_solve);
                img.setImageResource(R.drawable.ic_solve_sign);
            }
        }
    }
}
