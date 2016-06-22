package com.example.matan.intellignet;

import android.util.Log;

/**
 * Created by matan on 11/01/2016.
 */
public class TypeTashchezCell extends TypeGameCell
{
    public String cellType;
    public String solution;
    protected int answerNumOfLetter;
    public boolean regularTashchez;

    public TypeTashchezCell(String cellType, int answerNumOfLetter, int index, String content, String solution,  boolean regularTashchez)
    {
        super(index, content);
        this.cellType = cellType;
        this.answerNumOfLetter = answerNumOfLetter;
        this.regularTashchez = regularTashchez;
        this.background = backgroundByCellType(cellType);
        this.solution = solution;

    }


    protected void paintAnswerCells(int index)
    {
        //TODO
    }


    //set the background of cell according to cell Type
    protected int backgroundByCellType(String cellType)
    {

        if(cellType.contains("definition")) {
            if (regularTashchez)
                return R.drawable.ic_square_definition_higayon;
            else
                return R.drawable.ic_square_definition;
        }
        switch(cellType)
        {
            case TypeTashchezGrid.SOLVE:              return R.drawable.ic_square_solve;
            case TypeTashchezGrid.SOLVE_DOWN:         return R.drawable.ic_square_down;
            case TypeTashchezGrid.SOLVE_DOWN_LEFT:    return R.drawable.ic_square_down_left;
            case TypeTashchezGrid.SOLVE_LEFT:         return R.drawable.ic_square_left;
            case TypeTashchezGrid.SOLVE_LEFT_DOWN:    return R.drawable.ic_square_left_down;
            case TypeTashchezGrid.SOLVE_RIGHT_DOWN:   return R.drawable.ic_square_right_down;
            case TypeTashchezGrid.SOLVE_UP_LEFT:      return R.drawable.ic_square_up_left;
        }

        return TypeGameCell.ERROR;
    }


    public String getCellType() {
        return cellType;
    }
}
