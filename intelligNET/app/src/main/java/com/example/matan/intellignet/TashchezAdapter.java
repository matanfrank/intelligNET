package com.example.matan.intellignet;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by matan on 16/12/2015.
 */
public class TashchezAdapter extends ArrayAdapter<TypeTashchezCell> {
    private static final int DEFINITION = 0;
    private static final int SOLVE = 1;
    private static final int ERROR = -1;
    private static final int INIT_LAST_PAINT = -2;//init to know the word border

    private final int INIT_LAST_PAINT_COUNTER = -3;//init to know the word last letter. needed because when write the last letter need to init but if init to INIT_LAST_PAINT problem for erase
    private static final int DEF_CLICK = 4;
    private static final int SOLVE_CLICK = 5;
    private static final int FIRST_CLICK_SOLVE = 6;
    private static final int SECOND_CLICK_SOLVE = 7;
    private int whatClickSolve = FIRST_CLICK_SOLVE;
    private int slvLayout;
    private int defLayout;
    private LayoutInflater inflater;
    private InputMethodManager imm;
    private newEditText editText;
    private TextView definitionTextView;
    private int prevDef;  //remember the last deg clicked bcus dont want to do solve mode twice for the same def

    public static int j = 0;

    public static Activity activity;
    private int[] lastPaint;
    private Integer lastPaintCounter = new Integer(0);

    public static boolean setFocusDefClick = true;
    //public static Semaphore mutex = new Semaphore(100, true);
    private static boolean EditTextListenerCallAfter;
    private static boolean EditTextListenerCallOn;
    private static boolean onKeyListenerCall = true;
    public static android.os.Handler hKeyboard = new android.os.Handler();
    public static Runnable rKeyboard = null;
    private boolean clearFlag = false;
    public final EditText txtUrl = new EditText(getContext());

    private ShowAlertDialogInterface showAlertDialogInterface;
    private Timer T;
    private TimerTask TT;
    private String savedSolution;
    int touchCounter = 0;
    private GridView tashchezGrid;

    public TashchezAdapter(Activity activity, int defLayout, int slvLayout,String savedSolution, ArrayList data, GridView tashchezGrid) {//, TashchezPassEditText tashchezPassEditText) {
        super(activity, slvLayout, data);
        this.slvLayout = slvLayout;
        this.defLayout = defLayout;
        this.savedSolution = savedSolution;
        this.activity = activity;
        this.tashchezGrid = tashchezGrid;
        inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        definitionTextView = (TextView) activity.findViewById(R.id.definitionTextView);
        lastPaint = new int[TashchezUI.NUM_COL];
        for (int i = 0; i < lastPaint.length; i++)
            lastPaint[i] = INIT_LAST_PAINT;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }


    @Override
    public int getItemViewType(int position) {
        if (getItem(position).cellType.contains("definition"))
            return DEFINITION;
        else
            return SOLVE;
    }


    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {

        int type = getItemViewType(position);
        boolean firstTime = true;

        if (convertView == null) {
            switch (type) {
                case DEFINITION:
                    convertView = inflater.inflate(defLayout, parent, false);
                    break;

                case SOLVE:
                    convertView = inflater.inflate(slvLayout, parent, false);
                    break;
            }
        }
        else
            firstTime = false;


        final TypeTashchezCell tashchezCell = getItem(position);

        TextView textView;
        ImageView imageView;


        switch (type) {
            case DEFINITION:

                textView = (TextView) convertView.findViewById(R.id.tashchez_text_view);
                textView.setText(tashchezCell.content);
                textView.setOnClickListener(new View.OnClickListener() {
                                                int x;
                                                @Override
                                                public void onClick(View v) {
                                                    Log.d("gone", "11111111");
                                                    //if(!TashchezUI.clickOnErase)
                                                    prevDef = solveModeOn(tashchezCell, DEF_CLICK);
                                                }
                                            }
                );

                imageView = (ImageView) convertView.findViewById(R.id.tashchez_image_def);
                imageView.setImageResource(tashchezCell.background);
                break;


            case SOLVE:

                editText = (newEditText) convertView.findViewById(R.id.tashchez_edit_text);
                tashchezGrid.setLongClickable(true);

                //in case that the user saved a solution from previous time
                if(firstTime && savedSolution.length() > 0 && savedSolution.charAt(tashchezCell.index) != '*')
                {
                    editText.setText(String.valueOf(savedSolution.charAt(tashchezCell.index)));
                    //to change the letter to '*' because i dont want the software to remember the letter
//                String tempSavedSolution = savedSolution;
//                savedSolution = tempSavedSolution.substring(0,tashchezCell.index) + '*' + tempSavedSolution.substring(tashchezCell.index+1);

                }


                tashchezCell.setEditText(editText);


                imageView = (ImageView) convertView.findViewById(R.id.tashchez_image_solve);
                if (!tashchezCell.onEdit)
                    imageView.setImageResource(tashchezCell.background);
                else
                    imageView.setImageResource(R.drawable.ic_solve_sign);




                //when getting into solveMode the coverImage need to disappear when click on solve cell
                editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                    int x;

                    @Override
                    public void onFocusChange(View v, boolean hasFocus) {
                        Log.d("solveClick", "focus" + whatClickSolve);
//                        lastPaintCounter = findCursor();//TODO has to be here!!! need to know how to do it!
                        if (setFocusDefClick && !TashchezUI.clickOnErase)//every time call "requestFocus" and click on "erase all" dont get in
                        {
                            whatClickSolve = FIRST_CLICK_SOLVE;
                            prevDef = solveModeOn(tashchezCell, SOLVE_CLICK);
                            whatClickSolve = SECOND_CLICK_SOLVE;
                        }
                    }
                });


                editText.setOnKeyListener(new View.OnKeyListener() {
                    int x;
                    @Override
                    public boolean onKey(View v, int keyCode, KeyEvent event) {
                        int x;
                        if (keyCode == KeyEvent.KEYCODE_DEL) {

                            if (event.getAction() != KeyEvent.ACTION_UP)//keeping from several time that this happend automatically - not matter for app!
                                return true;

                            lastPaintCounter = findCursor();
                            Log.d("insane","lastainCounter: " +lastPaintCounter);

                            if (lastPaintCounter - 1 >= 0 && getItem(lastPaint[lastPaintCounter]).editText.getText().length() == 0)//know that the word didnt arrive to last letter yet  and cell empty so take the cursor one back
                            {
                                lastPaintCounter -= 1;

                                setFocusDefClick = false;
                                getItem(lastPaint[lastPaintCounter]).editText.requestFocus();
                                setFocusDefClick = true;
                            }
                            clearFlag = true;
                            getItem(lastPaint[lastPaintCounter]).editText.getText().clear();
                            clearFlag = false;
                        }
                        return false;
                    }
                });


                editText.setOnClickListener(new View.OnClickListener() {
                    int x;

                    @Override
                    public void onClick(View v) {
                        Log.d("solveClick", "click" + whatClickSolve);
                        if (whatClickSolve == FIRST_CLICK_SOLVE) {
                            whatClickSolve  = FIRST_CLICK_SOLVE;
                            prevDef = solveModeOn(tashchezCell, SOLVE_CLICK);
                            whatClickSolve = SECOND_CLICK_SOLVE;
                        } else if (whatClickSolve == SECOND_CLICK_SOLVE) {
                            prevDef = solveModeOn(tashchezCell, SOLVE_CLICK);
                            whatClickSolve = FIRST_CLICK_SOLVE;
                        }
                    }
                });


                //when user writing an answer the cursor has to jump automatically between cells
                editText.addTextChangedListener(new TextWatcher() {

                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                        if (!clearFlag) {
                            EditTextListenerCallAfter = true;
                            EditTextListenerCallOn = true;

//                            if(getItem(lastPaint[lastPaintCounter]).editText.getText().length() >= 1)
//                            {
//                                while ((lastPaintCounter + 1) < lastPaint.length && lastPaint[lastPaintCounter + 1] != INIT_LAST_PAINT &&
//                                        getItem(lastPaint[lastPaintCounter]).editText.getText().length() != 0)//in case that the cell is fill already
//                                    lastPaintCounter += 1;
//
//                                setFocusDefClick = false;
//                                getItem(lastPaint[lastPaintCounter]).editText.requestFocus();
//                                setFocusDefClick = true;
//                            }
                        }
                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count)//response on last letter correction
                    {
                        if (EditTextListenerCallOn)//in case of race condition because sometimes run 3 times
                        {
                            findCursor();

                            if (s.length() > 0 && (s.charAt(0)) == 'ם')
                                getItem(lastPaint[lastPaintCounter]).editText.setText(String.valueOf('מ'));
                            if (s.length() > 0 && (s.charAt(0)) == 'ן')
                                getItem(lastPaint[lastPaintCounter]).editText.setText(String.valueOf('נ'));
                            if (s.length() > 0 && (s.charAt(0)) == 'ץ')
                                getItem(lastPaint[lastPaintCounter]).editText.setText(String.valueOf('צ'));
                            if (s.length() > 0 && (s.charAt(0)) == 'ף')
                                getItem(lastPaint[lastPaintCounter]).editText.setText(String.valueOf('פ'));

                            EditTextListenerCallOn = false;
                        }
                    }

                    @Override
                    public void afterTextChanged(Editable s)//response on the cursor advance when writing
                    {
                        if (!clearFlag) {
                            if (EditTextListenerCallAfter)//in case of race condition because sometimes run 3 times
                            {
                                EditTextListenerCallAfter = false;

                                lastPaintCounter = findCursor();

                                if ((lastPaintCounter + 1) < lastPaint.length && lastPaint[lastPaintCounter + 1] != INIT_LAST_PAINT)//if "lastPaintCounter + 1" in the tashchez limits
                                {
                                    lastPaintCounter += 1;
                                    while ((lastPaintCounter + 1) < lastPaint.length && lastPaint[lastPaintCounter + 1] != INIT_LAST_PAINT &&
                                            getItem(lastPaint[lastPaintCounter]).editText.getText().length() != 0)//in case that the cell is fill already
                                        lastPaintCounter += 1;

                                    setFocusDefClick = false;
                                    getItem(lastPaint[lastPaintCounter]).editText.requestFocus();
                                    setFocusDefClick = true;
                                }
                            }
                        }
                    }
                });

                tashchezGrid.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener()  {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> arg0, View arg1, final int slvIndex, final long arg3) {
                        new AlertDialog.Builder(getContext())
                                .setTitle("עוזר אינטליג-NET-י")
                                .setMessage(MainActivity.user.getFirstName() + " " + MainActivity.user.getLastName() + ", נשארו לך עוד "+ "helpForDay" +" עזרות להיום.\nהאם אתה בטוח שברצונך לחשוף את הפתרון עבור משבצת זו?")
                                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {


                                        int slvIndexTemp=slvIndex, letterCounter = 0;
                                        TypeTashchezCell defCell = null;
                                        if(getItem(slvIndex) != null && getItem(slvIndex).cellType.contains("solve")) {
                                            {
                                                whatClickSolve = FIRST_CLICK_SOLVE;
                                                defCell = getItem(findDef(getItem(slvIndex)));
                                            }

                                            Log.d("sssssssssssss",defCell.getCellType().substring(defCell.getCellType().length()-4) + " " + slvIndex + arg3);


                                            //go to the right till def cell or till te first cell in the line
                                            while (defCell.getCellType().substring(defCell.getCellType().length()-4).contains("Left") && getItem(slvIndexTemp-1).getCellType().contains("solve") &&
                                                    slvIndexTemp % TashchezUI.NUM_ROW != 0) {
                                                slvIndexTemp--;
                                                letterCounter++;
                                            }


                                                while (defCell.getCellType().substring(defCell.getCellType().length()-4).contains("Down") && slvIndexTemp >= TashchezUI.NUM_COL &&
                                                        getItem(slvIndexTemp-TashchezUI.NUM_ROW).getCellType().contains("solve"))//go up till def cell or till te first cell in the column
                                                {
                                                    slvIndexTemp -= TashchezUI.NUM_ROW;
                                                    letterCounter++;
                                                }
                                            getItem(slvIndex).editText.setText(String.valueOf(defCell.solution.charAt(letterCounter)));
                                        }

                                    }
                                })
                                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        // do nothing
                                    }
                                })
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .show();
                        return false;
                    }
                });
        }
        return convertView;
    }


    //solveModeOn:
    //a - make the definition show on the top of the board.
    //b - make the cover of intelligNET disappear.
    //c - paint the answers cells that connected to that particular definition that just clicked.
    //d - set the cursor in the first cell of answer to that particular definition that just clicked.
    private int solveModeOn(final TypeTashchezCell tashchezCell, int solveOrDef) {

        final TypeTashchezCell cell;
        int defCell = ERROR;

        if(solveOrDef == SOLVE_CLICK) //if this is solve click need to find the "def cell"
        {
            defCell = findDef(tashchezCell);
            cell = getItem(defCell);

        }
        else
            cell = tashchezCell;

        //make the definition show on the top of the board

//        if(!(solveOrDef == SOLVE_CLICK && defCell == tashchezCell.getIndex()) && !TashchezUI.solveMode)
        {
            android.os.Handler hDef = new android.os.Handler();
            Runnable rDef = new Runnable() {
                @Override
                public void run() {

                    definitionTextView.setVisibility(View.VISIBLE);
                    definitionTextView.setText(cell.content);

                    if (rKeyboard != null)
                        hKeyboard.postDelayed(rKeyboard, 200);
                }
            };
            hDef.postDelayed(rDef, 300);


            //make the cover of intelligNET disappear
            TashchezUI.coverLayout.setVisibility(View.GONE);

            //paint the andswers cells tht connected to that particular definition tha just clicked
            paintAnswer(cell);

            //change to solveMode on right now
            TashchezUI.solveMode = true;


            //set the cursor in the first cell of answer to that particular definition that just clicked
            //note: lastPaint[0] is the first cell in the answer word.
            if (lastPaint[0] != INIT_LAST_PAINT) {
                if (solveOrDef == DEF_CLICK) {
                    j = 0;
                    //in case that the cell is fill already
                    while (j < lastPaint.length && lastPaint[j] != INIT_LAST_PAINT && getItem(lastPaint[j]).editText.getText().length() != 0)
                    {
                        j++;

                        //in case that all answer cell is full already the cursor wont know where to be bcuz of next "if"
                        //note: second case in "if" is for a case that all the cells full and the word is in the same length as the tashchez
                        if((j < lastPaint.length && lastPaint[j] == INIT_LAST_PAINT) ||
                                (j == lastPaint.length))
                        {
                            j--;
                            break;
                        }
                    }

                    rKeyboard = new Runnable() {
                        @Override
                        public void run() {
                            if ((j < lastPaint.length && lastPaint[j] != INIT_LAST_PAINT && getItem(lastPaint[j]).editText.getText().length() == 0) || /* regular case*/
                                    (j+1 < lastPaint.length && lastPaint[j+1] == INIT_LAST_PAINT && getItem(lastPaint[j]).editText.getText().length() != 0) || /*in case that all the cells alreay full*/
                                    (j+1 == lastPaint.length && getItem(lastPaint[j]).editText.getText().length() != 0))  /*in case that all the cells alreay full and this is the long word can be*/
                            {
                                setFocusDefClick = false;
                                getItem(lastPaint[j]).editText.requestFocus();
                                setFocusDefClick = true;
                                imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                                imm.showSoftInput(getItem(lastPaint[j]).editText, 0);
                            }
                        }
                    };

                } else if (solveOrDef == SOLVE_CLICK) {
                    rKeyboard = new Runnable() {
                        @Override
                        public void run() {
                            setFocusDefClick = false;
                            getItem(tashchezCell.getIndex()).editText.requestFocus();
                            setFocusDefClick = true;
                            imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                            imm.showSoftInput(getItem(tashchezCell.getIndex()).editText, 0);
                        }
                    };
                }
            }
        }
        return cell.getIndex();
    }



    private int findDef(final TypeTashchezCell tashchezCell)
    {
        int defIndex =  tashchezCell.getIndex();

        if(tashchezCell.getCellType().contains("definition"))//just "in case" that not supposed to happen
        {
            return tashchezCell.getIndex();
        }

        else {
            //HORIZONTAL
            if((whatClickSolve == FIRST_CLICK_SOLVE && ((((defIndex+1) < (TashchezUI.NUM_COL * TashchezUI.NUM_ROW) && (defIndex-1) >= 0) && /*general case*/
                    (getItem(defIndex + 1).getCellType().contains("solve") || getItem(defIndex - 1).getCellType().contains("solve"))) ||
                    (defIndex == (TashchezUI.NUM_COL * TashchezUI.NUM_ROW-1) && getItem(defIndex - 1).getCellType().contains("solve")) || /*last cell*/
                    (defIndex == 0 && getItem(defIndex + 1).getCellType().contains("solve"))))    || /*first cell*/
                    (whatClickSolve == SECOND_CLICK_SOLVE && (((defIndex+TashchezUI.NUM_COL) < (TashchezUI.NUM_COL * TashchezUI.NUM_ROW) && (defIndex-TashchezUI.NUM_COL) >= 0) &&
                            (getItem(defIndex + TashchezUI.NUM_COL).getCellType().contains("definition") && getItem(defIndex - TashchezUI.NUM_COL).getCellType().contains("definition"))))/*for second click and between 2 def and no option for vertical*/

                    /* in case that second click and cant make vertical bcuz of one up/down def and one up/down is out of board */
                    ||  (whatClickSolve == SECOND_CLICK_SOLVE && (((defIndex+TashchezUI.NUM_COL) >= (TashchezUI.NUM_COL * TashchezUI.NUM_ROW) && (defIndex-TashchezUI.NUM_COL) >= 0) &&
                    (getItem(defIndex - TashchezUI.NUM_COL).getCellType().contains("definition"))))   ||
                    (whatClickSolve == SECOND_CLICK_SOLVE && (((defIndex+TashchezUI.NUM_COL) < (TashchezUI.NUM_COL * TashchezUI.NUM_ROW) && (defIndex-TashchezUI.NUM_COL) < 0) &&
                            (getItem(defIndex + TashchezUI.NUM_COL).getCellType().contains("definition"))))
                    )

            {

                //go to the right till def cell or till te first cell in the line
                while (getItem(defIndex).getCellType().contains("solve") && defIndex % TashchezUI.NUM_ROW != 0)
                    defIndex--;

                //if we reached the first cell in the line and it is still "solve cell", the "def cell" can be: one up or one down
                if (getItem(defIndex).getCellType().contains("solve") && defIndex % TashchezUI.NUM_ROW == 0) {
                    if (getItem(defIndex).getCellType().contains("solveDownLeft"))
                        defIndex = defIndex - TashchezUI.NUM_COL;
                    else if (getItem(defIndex).getCellType().contains("solveUpLeft"))
                        defIndex = defIndex + TashchezUI.NUM_COL;
                }

                //if we "def cell", the "def cell" can be:
                //a - the cell we just reached
                //b - one cell forward and one up
                //c - one cell forward and one down
                else if (getItem(defIndex).getCellType().contains("definition")) {
                    //a - the cell we just reached
                    if (getItem(defIndex).getCellType().contains("definitionLeft"))
                        defIndex = defIndex; //do nothing

                        //b - one cell forward and one up
                        //c - one cell forward and one down
                    else {
                        defIndex++;

                        if (getItem(defIndex).getCellType().contains("solveDownLeft"))
                            defIndex = defIndex - TashchezUI.NUM_COL;
                        else if (getItem(defIndex).getCellType().contains("solveUpLeft"))
                            defIndex = defIndex + TashchezUI.NUM_COL;
                    }
                }
            }


//VERTICAL
            if((whatClickSolve == SECOND_CLICK_SOLVE && ((((defIndex+TashchezUI.NUM_COL) < (TashchezUI.NUM_COL * TashchezUI.NUM_ROW) && (defIndex-TashchezUI.NUM_COL) >= 0) && /*general case*/
                    (getItem(defIndex + TashchezUI.NUM_COL).getCellType().contains("solve") || getItem(defIndex - TashchezUI.NUM_COL).getCellType().contains("solve"))) ||
                    ((defIndex < TashchezUI.NUM_COL * TashchezUI.NUM_ROW) && (defIndex >= ((TashchezUI.NUM_COL-1) * TashchezUI.NUM_ROW)) && /*last  * line* */
                            (getItem(defIndex - TashchezUI.NUM_COL).getCellType().contains("solve"))) ||
                    ((defIndex >= 0 && defIndex < TashchezUI.NUM_COL)&&  /*first  * line* */
                            getItem(defIndex + TashchezUI.NUM_COL).getCellType().contains("solve"))))    ||
                    (whatClickSolve == FIRST_CLICK_SOLVE && (((defIndex+1) < (TashchezUI.NUM_COL * TashchezUI.NUM_ROW) && (defIndex-1) >= 0) && /*for first click and no option for horizon*/
                            (getItem(defIndex + 1).getCellType().contains("definition") && getItem(defIndex - 1).getCellType().contains("definition"))))

                    /* in case that first click and cant make horizontal bcuz of one right/left def and one right/left is out of board */
                    ||  (whatClickSolve == SECOND_CLICK_SOLVE && (((defIndex+1) >= (TashchezUI.NUM_COL * TashchezUI.NUM_ROW) && (defIndex-1) >= 0) &&
                    (getItem(defIndex - 1).getCellType().contains("definition"))))   ||
                    (whatClickSolve == SECOND_CLICK_SOLVE && (((defIndex+1) < (TashchezUI.NUM_COL * TashchezUI.NUM_ROW) && (defIndex-1) < 0) &&
                            (getItem(defIndex + 1).getCellType().contains("definition"))))
                    )
            {

                while (getItem(defIndex).getCellType().contains("solve") && defIndex >= TashchezUI.NUM_COL)//go up till def cell or till te first cell in the column
                    defIndex -= TashchezUI.NUM_ROW;

                //if we reached the first cell in the column and it is still "solve cell", the "def cell" can be: one left or one right
                if (getItem(defIndex).getCellType().contains("solve") && defIndex < TashchezUI.NUM_COL) {
                    if (getItem(defIndex).getCellType().contains("solveRightDown"))
                        defIndex++;
                    else if (getItem(defIndex).getCellType().contains("solveLeftDown"))
                        defIndex--;
                }

                //if we are in "def cell", the "def cell" can be:
                //a - the cell we just reached
                //b - one cell forward and one right
                //c - one cell forward and one left
                else if (getItem(defIndex).getCellType().contains("definition")) {
                    //a - the cell we just reached
                    if (getItem(defIndex).getCellType().contains("definitionDown"))
                        defIndex = defIndex; //do nothing

                        //b - one cell forward and one up
                        //c - one cell forward and one down
                    else {
                        defIndex += TashchezUI.NUM_COL;

                        if (getItem(defIndex).getCellType().contains("solveRightDown"))
                            defIndex++;
                        else if (getItem(defIndex).getCellType().contains("solveLeftDown"))
                            defIndex--;
                    }
                }
                //whatClickSolve = FIRST_CLICK_SOLVE;
            }

        }

        return defIndex;
    }



    private void paintAnswer(TypeTashchezCell typeTashchezCell)//paint the answer cells from white to red
    {
        int j = 0, i = 0;
        int toPaint = typeTashchezCell.index;

        while (i < lastPaint.length && lastPaint[i] != INIT_LAST_PAINT)//make the lastPaint cells back to white before painting the next answer cells
        {
            getItem(lastPaint[i]).onEdit = false;
            i++;
        }

        if (typeTashchezCell.cellType.contains("definition")) {
            for (i = 0; i < typeTashchezCell.answerNumOfLetter; i++) {
                if (typeTashchezCell.cellType.contains("Down") && typeTashchezCell.cellType.contains("Left") && typeTashchezCell.cellType.endsWith("Left")) {
                    if (i == 0)
                        toPaint += TashchezUI.NUM_ROW;
                    else
                        toPaint += 1;
                } else if (typeTashchezCell.cellType.contains("Left") && typeTashchezCell.cellType.contains("Down") && typeTashchezCell.cellType.endsWith("Down")) {
                    if (i == 0)
                        toPaint += 1;
                    else
                        toPaint += TashchezUI.NUM_ROW;
                } else if (typeTashchezCell.cellType.contains("Up") && typeTashchezCell.cellType.contains("Left") && typeTashchezCell.cellType.endsWith("Left")) {
                    if (i == 0)
                        toPaint -= TashchezUI.NUM_ROW;
                    else
                        toPaint += 1;
                } else if (typeTashchezCell.cellType.contains("Right") && typeTashchezCell.cellType.contains("Down") && typeTashchezCell.cellType.endsWith("Down")) {
                    if (i == 0)
                        toPaint -= 1;
                    else
                        toPaint += TashchezUI.NUM_ROW;
                } else if (typeTashchezCell.cellType.contains("Left"))
                    toPaint += 1;
                else if (typeTashchezCell.cellType.contains("Down"))
                    toPaint += TashchezUI.NUM_ROW;

                getItem(toPaint).onEdit = true;
                lastPaint[j] = toPaint;
                j++;
            }
        }
        this.notifyDataSetChanged();

        for (; j < lastPaint.length; j++)//init the cells in lastPaint[] that not in use in a case of an answer with less that NUM_COL letters
            lastPaint[j] = INIT_LAST_PAINT;

    }






    public void setShowAlertInterface(ShowAlertDialogInterface pass) {
        showAlertDialogInterface = pass;
    }


    public String createContentToSave()
    {
        String content = "";
        for(int i=0 ; i < TashchezUI.NUM_COL*TashchezUI.NUM_ROW ; i++)
        {
            if(getItem(i).getCellType().contains("definition") || getItem(i).editText.getText().toString().equals(""))
                content += "*";
            else
                content += getItem(i).editText.getText().toString();
        }

        Log.d("1212121212", content);
        return content;
    }

//    public void getContentToSaved() {
//        //in case that the user saved a solution from previous time
//        for(int i=0 ; i<TashchezUI.NUM_COL*TashchezUI.NUM_ROW ; i++) {
//            if (savedSolution.length() > 0 && savedSolution.charAt(i) != '*') {
//                getItem(i).editText.setText(String.valueOf(savedSolution.charAt(i)));
////                getItem(lastPaint[lastPaintCounter]).editText.setText(String.valueOf('מ'));
//                //to change the letter to '*' because i dont want the software to remember the letter
//                String tempSavedSolution = savedSolution;
//                savedSolution = tempSavedSolution.substring(0, i) + '*' + tempSavedSolution.substring(i+1);
//                Log.d("inside","inside " + getItem(i).editText.getText());
//            }
//        }
//        this.notifyDataSetChanged();
//        synchronized(this){
//            this.notifyAll();
//        }
//        this.setNotifyOnChange(true);
//
//    }



    private int findCursor() {
        for (int i = 0; i < lastPaint.length; i++)//to know where the cursor right now
            if (lastPaint[i] != INIT_LAST_PAINT && getItem(lastPaint[i]).editText.hasFocus())
                return i;
        return ERROR;
    }

}
