package com.example.matan.intellignet;

/**
 * Created by matan on 11/01/2016.
 */
public abstract class TypeGameCell
{
    protected int background;
    protected int xLocation;
    protected int yLocation;
    protected String content;
    protected int index;

    protected TypeGameCell(int background, int index, String content)
    {
        this.background = background;
        this.xLocation = xLocation;
        this.yLocation = yLocation;
        this.content = content;
        this.index = index;
    }

    protected void answerCell(int xLocation, int yLocation)
    {

    }

    protected abstract void paintAnswerCells(int xLocation, int yLocation);

}
