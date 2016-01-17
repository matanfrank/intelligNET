package com.example.matan.intellignet;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
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





    }
}
