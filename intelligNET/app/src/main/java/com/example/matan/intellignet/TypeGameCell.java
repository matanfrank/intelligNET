package com.example.matan.intellignet;

/**
 * Created by matan on 11/01/2016.
 */
public abstract class TypeGameCell
{
    protected static final int ERROR = -1;
    protected int background;
    protected String content;
    protected int index;
    protected boolean onEdit;

    protected TypeGameCell(int index, String content)
    {
        this.content = content;
        this.index = index;
        this.onEdit = false;
    }

    //set the background of cell according to cell Type
    protected abstract int backgroundByCellType(String cellType);

    protected void answerCell(int index)//TODO
    {

    }

    protected void paintAnswerCells()//TODO
    {

    }

    protected void openKeyboard()//TODO
    {

    }


}
