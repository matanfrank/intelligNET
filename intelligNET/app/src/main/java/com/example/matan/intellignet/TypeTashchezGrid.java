package com.example.matan.intellignet;

import java.util.ArrayList;

/**
 * Created by matan on 11/01/2016.
 */
public class TypeTashchezGrid extends TypeGameGrid<TypeTashchezCell>
{
    protected final static String SOLVE_LEFT = "solveLeft";
    protected final static String SOLVE_DOWN = "solveDown";
    protected final static String SOLVE_DOWN_LEFT = "solveDownLeft";
    protected final static String SOLVE_UP_LEFT = "solveUpLeft";
    protected final static String SOLVE = "solve";
    protected final static String SOLVE_RIGHT_DOWN = "solveRightDown";
    protected final static String SOLVE_LEFT_DOWN = "solveLeftDown";
    protected final static String DEFINITION_LEFT = "definitionLeft";
    protected final static String DEFINITION_DOWN = "definitionDown";
    protected final static String DEFINITION_DOWN_LEFT  = "definitionDownLeft";
    protected final static String DEFINITION_UP_LEFT = "definitionUpLeft";
    protected final static String DEFINITION_RIGHT_DOWN = "definitionRightDown";
    protected final static String DEFINITION_LEFT_DOWN  = "definitionLeftDown";

    protected boolean tashchezProperties;


    protected  TypeTashchezGrid(int row, int col, boolean prop, String data)
      {
        super(col, row, prop);//, data);

        board = new ArrayList<TypeTashchezCell> ();

        for (int i = 0;   i < (row*col); i++)
        {
            switch (i)
            {
                case 0:
                    this.board.add(new TypeTashchezCell(DEFINITION_DOWN_LEFT, 7, i, "פקודה צבאית (4,3)"));
                    break;
                case 1:
                    this.board.add(new TypeTashchezCell(SOLVE_RIGHT_DOWN, 0, i, ""));
                    break;
                case 2:
                    this.board.add(new TypeTashchezCell(DEFINITION_RIGHT_DOWN, 7, i, "עיר בירה באירופה"));
                    break;
                case 3:
                    this.board.add(new TypeTashchezCell(DEFINITION_DOWN, 5, i, "בעל חיים קוצני"));
                    break;
                case 4:
                    this.board.add(new TypeTashchezCell(DEFINITION_LEFT_DOWN, 3, i, "רפורט"));
                    break;
                case 5:
                    this.board.add(new TypeTashchezCell(SOLVE_LEFT_DOWN, 0, i, ""));
                    break;
                case 6:
                    this.board.add(new TypeTashchezCell(DEFINITION_DOWN, 3, i, "משך חיים ממוצע"));
                    break;
                case 7:
                    this.board.add(new TypeTashchezCell(SOLVE_DOWN_LEFT, 0, i, ""));
                    break;
                case 8:
                    this.board.add(new TypeTashchezCell(SOLVE, 0, i, ""));
                    break;
                case 9:
                    this.board.add(new TypeTashchezCell(SOLVE, 0, i, ""));
                    break;
                case 10:
                    this.board.add(new TypeTashchezCell(SOLVE_DOWN, 0, i, ""));
                    break;
                case 11:
                    this.board.add(new TypeTashchezCell(SOLVE, 0, i, ""));
                    break;
                case 12:
                    this.board.add(new TypeTashchezCell(SOLVE, 0, i, ""));
                    break;
                case 13:
                    this.board.add(new TypeTashchezCell(SOLVE_DOWN, 0, i, ""));
                    break;
                case 14:
                    this.board.add(new TypeTashchezCell(DEFINITION_DOWN_LEFT, 5, i, "מפיצה ריח נעים"));
                    break;
                case 15:
                    this.board.add(new TypeTashchezCell(SOLVE, 0, i, ""));
                    break;
                case 16:
                    this.board.add(new TypeTashchezCell(DEFINITION_DOWN, 2, i, "אנקול"));
                    break;
                case 17:
                    this.board.add(new TypeTashchezCell(SOLVE, 0, i, ""));
                    break;
                case 18:
                    this.board.add(new TypeTashchezCell(DEFINITION_LEFT, 2, i, "סיכה לקישוט"));
                    break;
                case 19:
                    this.board.add(new TypeTashchezCell(SOLVE_LEFT, 0, i, ""));
                    break;
                case 20:
                    this.board.add(new TypeTashchezCell(SOLVE, 0, i, ""));
                    break;
                case 21:
                    this.board.add(new TypeTashchezCell(SOLVE_DOWN_LEFT, 0, i, ""));
                    break;
                case 22:
                    this.board.add(new TypeTashchezCell(SOLVE, 0, i, ""));
                    break;
                case 23:
                    this.board.add(new TypeTashchezCell(SOLVE_DOWN, 0, i, ""));
                    break;
                case 24:
                    this.board.add(new TypeTashchezCell(SOLVE, 0, i, ""));
                    break;
                case 25:
                    this.board.add(new TypeTashchezCell(SOLVE, 0, i, ""));
                    break;
                case 26:
                    this.board.add(new TypeTashchezCell(DEFINITION_DOWN, 3, i, "רדאר"));
                    break;
                case 27:
                    this.board.add(new TypeTashchezCell(SOLVE, 0, i, ""));
                    break;
                case 28:
                    this.board.add(new TypeTashchezCell(DEFINITION_LEFT, 3, i, "ההפך ממיעוט"));
                    break;
                case 29:
                    this.board.add(new TypeTashchezCell(SOLVE_LEFT, 0, i, ""));
                    break;
                case 30:
                    this.board.add(new TypeTashchezCell(SOLVE, 0, i, ""));
                    break;
                case 31:
                    this.board.add(new TypeTashchezCell(SOLVE, 0, i, ""));
                    break;
                case 32:
                    this.board.add(new TypeTashchezCell(DEFINITION_DOWN, 2, i, "בשבילי"));
                    break;
                case 33:
                    this.board.add(new TypeTashchezCell(SOLVE_DOWN, 0, i, ""));
                    break;
                case 34:
                    this.board.add(new TypeTashchezCell(DEFINITION_DOWN, 2, i, "נוזל בגוף"));
                    break;
                case 35:
                    this.board.add(new TypeTashchezCell(DEFINITION_DOWN_LEFT, 3, i, "מושב דתי ליד אור יהודה"));
                    break;
                case 36:
                    this.board.add(new TypeTashchezCell(SOLVE, 0, i, ""));
                    break;
                case 37:
                    this.board.add(new TypeTashchezCell(DEFINITION_LEFT, 4, i, "נתפס"));
                    break;
                case 38:
                    this.board.add(new TypeTashchezCell(SOLVE_LEFT, 0, i, ""));
                    break;
                case 39:
                    this.board.add(new TypeTashchezCell(SOLVE_DOWN, 0, i, ""));
                    break;
                case 40:
                    this.board.add(new TypeTashchezCell(SOLVE, 0, i, ""));
                    break;
                case 41:
                    this.board.add(new TypeTashchezCell(SOLVE_DOWN, 0, i, ""));
                    break;
                case 42:
                    this.board.add(new TypeTashchezCell(SOLVE_DOWN_LEFT, 0, i, ""));
                    break;
                case 43:
                    this.board.add(new TypeTashchezCell(SOLVE, 0, i, ""));
                    break;
                case 44:
                    this.board.add(new TypeTashchezCell(SOLVE, 0, i, ""));
                    break;
                case 45:
                    this.board.add(new TypeTashchezCell(DEFINITION_LEFT, 3, i, "יחידה מרכזית מיוחדת"));
                    break;
                case 46:
                    this.board.add(new TypeTashchezCell(SOLVE_LEFT, 0, i, ""));
                    break;
                case 47:
                    this.board.add(new TypeTashchezCell(SOLVE, 0, i, ""));
                    break;
                case 48:
                    this.board.add(new TypeTashchezCell(SOLVE, 0, i, ""));
                    break;
            }
        }

    }


    protected boolean check()
    {
        return true;
    }

}
