package com.example.matan.intellignet;

/**
 * Created by matan on 11/01/2016.
 */
public class TypeTashchezCell extends TypeGameCell
{
    public String cellType;
    protected int answerNumOfLetter;

    public TypeTashchezCell(String cellType, int answerNumOfLetter, int background, int index, String content)
    {
        super(background, index, content);
        this.cellType = cellType;
        this.answerNumOfLetter = answerNumOfLetter;
    }

    protected void paintAnswerCells(int index)
    {
        //TODO
    }
}
