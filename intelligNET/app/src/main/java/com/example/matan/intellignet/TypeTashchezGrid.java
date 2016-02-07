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
                    this.board.add(new TypeTashchezCell(DEFINITION_DOWN_LEFT, 7, R.drawable.ic_square_definition, i, "פקודה צבאית (4,3)"));
                    break;
                case 1:
                    this.board.add(new TypeTashchezCell(SOLVE_RIGHT_DOWN, 0, R.drawable.ic_square_right_down, i, ""));
                    break;
                case 2:
                    this.board.add(new TypeTashchezCell(DEFINITION_RIGHT_DOWN, 7, R.drawable.ic_square_definition, i, "עיר בירה באירופה"));
                    break;
                case 3:
                    this.board.add(new TypeTashchezCell(DEFINITION_DOWN, 5, R.drawable.ic_square_definition, i, "בעל חיים קוצני"));
                    break;
                case 4:
                    this.board.add(new TypeTashchezCell(DEFINITION_LEFT_DOWN, 3, R.drawable.ic_square_definition, i, "רפורט"));
                    break;
                case 5:
                    this.board.add(new TypeTashchezCell(SOLVE_LEFT_DOWN, 0, R.drawable.ic_square_left_down, i, ""));
                    break;
                case 6:
                    this.board.add(new TypeTashchezCell(DEFINITION_DOWN, 3, R.drawable.ic_square_definition, i, "משך חיים ממוצע"));
                    break;
                case 7:
                    this.board.add(new TypeTashchezCell(SOLVE_DOWN_LEFT, 0, R.drawable.ic_square_down_left, i, ""));
                    break;
                case 8:
                    this.board.add(new TypeTashchezCell(SOLVE, 0, R.drawable.ic_square_solve, i, ""));
                    break;
                case 9:
                    this.board.add(new TypeTashchezCell(SOLVE, 0, R.drawable.ic_square_solve, i, ""));
                    break;
                case 10:
                    this.board.add(new TypeTashchezCell(SOLVE, 0, R.drawable.ic_square_down, i, ""));
                    break;
                case 11:
                    this.board.add(new TypeTashchezCell(SOLVE, 0, R.drawable.ic_square_solve, i, ""));
                    break;
                case 12:
                    this.board.add(new TypeTashchezCell(SOLVE, 0, R.drawable.ic_square_solve, i, ""));
                    break;
                case 13:
                    this.board.add(new TypeTashchezCell(SOLVE_DOWN, 0, R.drawable.ic_square_down, i, ""));
                    break;
                case 14:
                    this.board.add(new TypeTashchezCell(DEFINITION_DOWN_LEFT, 5, R.drawable.ic_square_definition, i, "מפיצה ריח נעים"));
                    break;
                case 15:
                    this.board.add(new TypeTashchezCell(SOLVE, 0, R.drawable.ic_square_solve, i, ""));
                    break;
                case 16:
                    this.board.add(new TypeTashchezCell(DEFINITION_DOWN, 2, R.drawable.ic_square_definition, i, "אנקול"));
                    break;
                case 17:
                    this.board.add(new TypeTashchezCell(SOLVE, 0, R.drawable.ic_square_solve, i, ""));
                    break;
                case 18:
                    this.board.add(new TypeTashchezCell(DEFINITION_LEFT, 2, R.drawable.ic_square_definition, i, "סיכה לקישוט"));
                    break;
                case 19:
                    this.board.add(new TypeTashchezCell(SOLVE_LEFT, 0, R.drawable.ic_square_left, i, ""));
                    break;
                case 20:
                    this.board.add(new TypeTashchezCell(SOLVE, 0, R.drawable.ic_square_solve, i, ""));
                    break;
                case 21:
                    this.board.add(new TypeTashchezCell(SOLVE_DOWN_LEFT, 0, R.drawable.ic_square_down_left, i, ""));
                    break;
                case 22:
                    this.board.add(new TypeTashchezCell(SOLVE, 0, R.drawable.ic_square_solve, i, ""));
                    break;
                case 23:
                    this.board.add(new TypeTashchezCell(SOLVE_DOWN, 0, R.drawable.ic_square_down, i, ""));
                    break;
                case 24:
                    this.board.add(new TypeTashchezCell(SOLVE, 0, R.drawable.ic_square_solve, i, ""));
                    break;
                case 25:
                    this.board.add(new TypeTashchezCell(SOLVE, 0, R.drawable.ic_square_solve, i, ""));
                    break;
                case 26:
                    this.board.add(new TypeTashchezCell(DEFINITION_DOWN, 3, R.drawable.ic_square_definition, i, "רדאר"));
                    break;
                case 27:
                    this.board.add(new TypeTashchezCell(SOLVE, 0, R.drawable.ic_square_solve, i, ""));
                    break;
                case 28:
                    this.board.add(new TypeTashchezCell(DEFINITION_LEFT, 3, R.drawable.ic_square_definition, i, "ההפך ממיעוט"));
                    break;
                case 29:
                    this.board.add(new TypeTashchezCell(SOLVE_LEFT, 0, R.drawable.ic_square_left, i, ""));
                    break;
                case 30:
                    this.board.add(new TypeTashchezCell(SOLVE, 0, R.drawable.ic_square_solve, i, ""));
                    break;
                case 31:
                    this.board.add(new TypeTashchezCell(SOLVE, 0, R.drawable.ic_square_solve, i, ""));
                    break;
                case 32:
                    this.board.add(new TypeTashchezCell(DEFINITION_DOWN, 2, R.drawable.ic_square_definition, i, "בשבילי"));
                    break;
                case 33:
                    this.board.add(new TypeTashchezCell(SOLVE_DOWN, 0, R.drawable.ic_square_down, i, ""));
                    break;
                case 34:
                    this.board.add(new TypeTashchezCell(DEFINITION_DOWN, 2, R.drawable.ic_square_definition, i, "נוזל בגוף"));
                    break;
                case 35:
                    this.board.add(new TypeTashchezCell(DEFINITION_DOWN_LEFT, 3, R.drawable.ic_square_definition, i, "מושב דתי ליד אור יהודה"));
                    break;
                case 36:
                    this.board.add(new TypeTashchezCell(SOLVE, 0, R.drawable.ic_square_solve, i, ""));
                    break;
                case 37:
                    this.board.add(new TypeTashchezCell(DEFINITION_LEFT, 4, R.drawable.ic_square_definition, i, "נתפס"));
                    break;
                case 38:
                    this.board.add(new TypeTashchezCell(SOLVE_LEFT, 0, R.drawable.ic_square_left, i, ""));
                    break;
                case 39:
                    this.board.add(new TypeTashchezCell(SOLVE, 0, R.drawable.ic_square_down, i, ""));
                    break;
                case 40:
                    this.board.add(new TypeTashchezCell(SOLVE, 0, R.drawable.ic_square_solve, i, ""));
                    break;
                case 41:
                    this.board.add(new TypeTashchezCell(SOLVE, 0, R.drawable.ic_square_down, i, ""));
                    break;
                case 42:
                    this.board.add(new TypeTashchezCell(SOLVE_DOWN_LEFT, 0, R.drawable.ic_square_down_left, i, ""));
                    break;
                case 43:
                    this.board.add(new TypeTashchezCell(SOLVE, 0, R.drawable.ic_square_solve, i, ""));
                    break;
                case 44:
                    this.board.add(new TypeTashchezCell(SOLVE, 0, R.drawable.ic_square_solve, i, ""));
                    break;
                case 45:
                    this.board.add(new TypeTashchezCell(DEFINITION_LEFT, 3, R.drawable.ic_square_definition, i, "יחידה מרכזית מיוחדת"));
                    break;
                case 46:
                    this.board.add(new TypeTashchezCell(SOLVE_LEFT, 0, R.drawable.ic_square_left, i, ""));
                    break;
                case 47:
                    this.board.add(new TypeTashchezCell(SOLVE, 0, R.drawable.ic_square_solve, i, ""));
                    break;
                case 48:
                    this.board.add(new TypeTashchezCell(SOLVE, 0, R.drawable.ic_square_solve, i, ""));
                    break;
            }
        }


//            if(i==0 || i==2 || i==3 || i==4 || i==6 || i==14 || i==16 || i==18 || i==26 || i==28 || i==32 || i==34 || i==35|| i==37 || i==45)
//                this.board.add(new TypeTashchezCell(SOLVE, 0, R.drawable.ic_square_definition, i, "פקודה צבאית (4,3)"));
//            else if(i==10 || i==13 || i==23 || i==33 || i==39 || i==41)
//                this.board.add(new TypeTashchezCell(SOLVE, 0, R.drawable.ic_square_down, i, ""));
//            else if(i==19 || i==29 || i==46 || i==38)
//                this.board.add(new TypeTashchezCell(SOLVE, 0, R.drawable.ic_square_left, i, ""));
//            else if(i==7 || i==21 || i==42)
//                this.board.add(new TypeTashchezCell(SOLVE, 0, R.drawable.ic_square_down_left, i, ""));
//            else if(i==5 || i==100)
//                this.board.add(new TypeTashchezCell(SOLVE, 0, R.drawable.ic_square_left_down, i, ""));
//            else if(i==1 || i==100)
//                this.board.add(new TypeTashchezCell(SOLVE, 0, R.drawable.ic_square_right_down, i, ""));
//            else if(i==100 || i==100)
//                this.board.add(new TypeTashchezCell(SOLVE, 0, R.drawable.ic_square_up_left, i, ""));
//            else
//                this.board.add(new TypeTashchezCell(SOLVE, 0, R.drawable.ic_square_solve, i, ""));

    }


    protected boolean check()
    {
        return true;
    }

}
