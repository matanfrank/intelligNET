package com.example.matan.intellignet;

import java.util.ArrayList;

/**
 * Created by matan on 08/01/2016.
 */
public abstract class TypeGameGrid
{
    public static int numColumns;
    public static int numRows;
    protected boolean properties;
    protected ArrayList<TypeGameCell> board;
    protected String data;

    protected TypeGameGrid(int col, int row, boolean prop, String data)
    {
        numColumns = col;
        numRows = row;
        properties = prop;
        this.data = data;
    }

    protected abstract boolean check();

    protected void answerAll()
    {

    }

}
