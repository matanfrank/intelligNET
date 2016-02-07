package com.example.matan.intellignet;

import java.util.ArrayList;

/**
 * Created by matan on 08/01/2016.
 */
public abstract class TypeGameGrid<T>
{
    public static int numColumns;
    public static int numRows;
    protected boolean properties;
    protected ArrayList<T> board;
    protected String data;

    protected TypeGameGrid(int col, int row, boolean prop)//, ArrayList<T> cells)
    {
        numColumns = col;
        numRows = row;
        properties = prop;
        this.data = data;
      ///  board = cells;
    }

    //init(TypeGameCell cells)
   // {
    //    board = cells
    //}
    protected abstract boolean check();

    protected void answerAll()
    {

    }

}
