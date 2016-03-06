package com.example.matan.intellignet;

/**
 * Created by matan on 11/01/2016.
 */
public class TypeTashchezCell extends TypeGameCell
{
    public String cellType;
    protected int answerNumOfLetter;

    public TypeTashchezCell(String cellType, int answerNumOfLetter, int index, String content)
    {
        super(index, content);
        this.cellType = cellType;
        this.answerNumOfLetter = answerNumOfLetter;
        this.background = backgroundByCellType(cellType);
    }


    protected void paintAnswerCells(int index)
    {
        //TODO
    }


    //set the background of cell according to cell Type
    protected int backgroundByCellType(String cellType)
    {
        if(cellType.contains("definition"))
            return R.drawable.ic_square_definition;

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
}
