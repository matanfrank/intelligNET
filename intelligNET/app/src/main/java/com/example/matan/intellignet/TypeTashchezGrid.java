package com.example.matan.intellignet;

import java.util.ArrayList;

/**
 * Created by matan on 11/01/2016.
 */
public class TypeTashchezGrid extends TypeGameGrid
{
    protected final static String LEFT = "left";
    protected final static String DOWN = "down";
    protected final static String DOWN_LEFT = "downLeft";
    protected final static String DEFINITION = "definition";
    protected final static String UP_LEFT = "upLeft";
    protected final static String SOLVE = "solve";
    protected final static String RIGHT_DOWN = "rightDown";
    protected final static String LEFT_DOWN = "leftDown";


    protected boolean tashchezProperties;


    protected  TypeTashchezGrid(int row, int col, boolean prop, String data)
    {
        super(col, row, prop, data);
        this.board = new ArrayList<>();

        for (int i = 0; i < (row*col); i++)
        {
            if(i==0 || i==2 || i==3 || i==4 || i==6 || i==14 || i==16 || i==18 || i==26 || i==28 || i==32 || i==34 || i==35|| i==37 || i==45)
                this.board.add(new TypeTashchezCell(SOLVE, 0, R.drawable.ic_square_definition, i, "פקודה צבאית (4,3)"));
            else if(i==10 || i==13 || i==23 || i==33 || i==39 || i==41)
                this.board.add(new TypeTashchezCell(SOLVE, 0, R.drawable.ic_square_down, i, ""));
            else if(i==19 || i==29 || i==46 || i==38)
                this.board.add(new TypeTashchezCell(SOLVE, 0, R.drawable.ic_square_left, i, ""));
            else if(i==7 || i==21 || i==42)
                this.board.add(new TypeTashchezCell(SOLVE, 0, R.drawable.ic_square_down_left, i, ""));
            else if(i==5 || i==100)
                this.board.add(new TypeTashchezCell(SOLVE, 0, R.drawable.ic_square_left_down, i, ""));
            else if(i==1 || i==100)
                this.board.add(new TypeTashchezCell(SOLVE, 0, R.drawable.ic_square_right_down, i, ""));
            else if(i==100 || i==100)
                this.board.add(new TypeTashchezCell(SOLVE, 0, R.drawable.ic_square_up_left, i, ""));
            else
                this.board.add(new TypeTashchezCell(SOLVE, 0, R.drawable.ic_square_solve, i, ""));

        }


    }


    protected boolean check()
    {
        return true;
    }

}
