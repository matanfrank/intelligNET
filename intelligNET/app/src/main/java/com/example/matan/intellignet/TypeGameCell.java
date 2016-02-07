package com.example.matan.intellignet;

/**
 * Created by matan on 11/01/2016.
 */
public abstract class TypeGameCell
{
    protected int background;
    protected String content;
    protected int index;

    protected TypeGameCell(int background, int index, String content)
    {
        this.background = background;
        this.content = content;
        this.index = index;
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


}
