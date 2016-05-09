package com.example.matan.intellignet;

import android.widget.EditText;

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
    protected newEditText editText; //added for automatic cursor move when write answer

    protected TypeGameCell(int index, String content)
    {
        this.content = content;
        this.index = index;
        this.onEdit = false;
    }

    //set the background of cell according to cell Type
    protected abstract int backgroundByCellType(String cellType);

    protected void setEditText(newEditText editText)
    {
        this.editText = editText;
    }

    protected void answerCell(int index)//TODO
    {

    }

    protected void paintAnswerCells()//TODO
    {

    }

    protected void openKeyboard()//TODO
    {

    }

    protected int getIndex() {
        return index;
    }
}
