package com.example.matan.intellignet;

import android.app.Activity;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by matan on 16/12/2015.
 */
public class TashchezAdapter extends ArrayAdapter<TypeTashchezCell> {
    private static final int DEFINITION = 0;
    private static final int SOLVE = 1;
    private static final int ERROR = -1;
    private static final int INIT = -2;

    private final int LAST_PAINT_COUNTER_INIT = -3;//needed because when right the last letter need to init but if init to zero problem for erase
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
    public static int j = 0;

    public static Activity activity;
    private int[] lastPaint;
    private Integer lastPaintCounter = new Integer(0);

    public static boolean setFocusDefClick = true;
    //public static Semaphore mutex = new Semaphore(100, true);
    private static boolean EditTextListenerCall;
    private static boolean onKeyListenerCall = true;
    public static android.os.Handler hKeyboard = new android.os.Handler();
    public static Runnable rKeyboard = null;
    private boolean clearFlag = false;
    public final EditText txtUrl = new EditText(getContext());

    private ShowAlertDialogInterface showAlertDialogInterface;
    private Timer T;
    private TimerTask TT;
    int touchCounter = 0;

    public TashchezAdapter(Activity activity, int defLayout, int slvLayout, ArrayList data) {//, TashchezPassEditText tashchezPassEditText) {
        super(activity, slvLayout, data);
        this.slvLayout = slvLayout;
        this.defLayout = defLayout;
        this.activity = activity;
        inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        definitionTextView = (TextView) activity.findViewById(R.id.definitionTextView);
        lastPaint = new int[TashchezUI.NUM_COL];
        for (int i = 0; i < lastPaint.length; i++)
            lastPaint[i] = INIT;
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
                                                    solveModeOn(tashchezCell, DEF_CLICK, ERROR);
                                                }
                                            }
                );

                imageView = (ImageView) convertView.findViewById(R.id.tashchez_image_def);
                imageView.setImageResource(tashchezCell.background);
                break;


            case SOLVE:

                editText = (newEditText) convertView.findViewById(R.id.tashchez_edit_text);

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
                        if (setFocusDefClick && !TashchezUI.clickOnErase)//every time call "requestFocus" and click on "erase all" dont get in
                        {
                            whatClickSolve = FIRST_CLICK_SOLVE;
                            solveModeOn(tashchezCell, SOLVE_CLICK, whatClickSolve);
                            whatClickSolve = SECOND_CLICK_SOLVE;
                        }
                    }
                });


                editText.setOnKeyListener(new View.OnKeyListener() {
                    private final Lock mutex2 = new ReentrantLock(true);

                    @Override
                    public boolean onKey(View v, int keyCode, KeyEvent event) {

                        mutex2.lock();
                        if (onKeyListenerCall) {
                            onKeyListenerCall = false;
                            if (keyCode == KeyEvent.KEYCODE_DEL) {
                                Log.d("erase", "erase");
                                if (lastPaintCounter - 1 >= 0) {
                                    lastPaintCounter -= 1;
                                    setFocusDefClick = false;
                                    getItem(lastPaint[lastPaintCounter]).editText.requestFocus();
                                    setFocusDefClick = true;
                                    clearFlag = true;
                                    getItem(lastPaint[lastPaintCounter]).editText.getText().clear();
                                    clearFlag = false;
                                }


                                if (lastPaintCounter == LAST_PAINT_COUNTER_INIT) {//last cell to erase (the different is jumping back)
                                    int i;
                                    for (i = 0; i < lastPaint.length && lastPaint[i] != INIT; i++);//get to the last cell of the answer
                                    i--;
                                    clearFlag = true;
                                    getItem(lastPaint[i]).editText.getText().clear();
                                    clearFlag = false;
                                    lastPaintCounter = i;

                                }
                                mutex2.unlock();
                            }
                        } else {
                            onKeyListenerCall = true;
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
                            solveModeOn(tashchezCell, SOLVE_CLICK, whatClickSolve);
                            whatClickSolve = SECOND_CLICK_SOLVE;
                        } else if (whatClickSolve == SECOND_CLICK_SOLVE) {
                            solveModeOn(tashchezCell, SOLVE_CLICK, whatClickSolve);
                            whatClickSolve = FIRST_CLICK_SOLVE;
                        }
                    }
                });


                //when user writing an answer the cursor has to jump automatically between cells
                editText.addTextChangedListener(new TextWatcher() {

                    private final Lock mutex1 = new ReentrantLock(true);

                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                        if (!clearFlag) {
                            EditTextListenerCall = true;
                            if (lastPaintCounter == LAST_PAINT_COUNTER_INIT)
                                lastPaintCounter = 0;
                        }


                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        if (!clearFlag) {
                            final StringBuilder sb = new StringBuilder(s.length());
                            sb.append(s);

                            mutex1.lock();//in case of race condition because sometimes run 3 times

                            if (EditTextListenerCall) {

                                for (int i = 0; i < lastPaint.length; i++)//to know where the cursor right now
                                    if (lastPaint[i] != INIT && getItem(lastPaint[i]).editText.hasFocus())
                                        lastPaintCounter = i;

                                EditTextListenerCall = false;


                                if ((lastPaintCounter + 1) < lastPaint.length && lastPaint[lastPaintCounter + 1] != INIT) {
                                    lastPaintCounter += 1;
                                    while ((lastPaintCounter + 1) < lastPaint.length && lastPaint[lastPaintCounter + 1] != INIT &&
                                            getItem(lastPaint[lastPaintCounter]).editText.getText().length() != 0)//in case that the cell is fill already
                                    {
                                        lastPaintCounter += 1;
                                    }
                                    setFocusDefClick = false;
                                    getItem(lastPaint[lastPaintCounter]).editText.requestFocus();
                                    setFocusDefClick = true;
                                    imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);//
                                    imm.showSoftInput(getItem(lastPaint[j]).editText, 0);
                                    imm.restartInput(getItem(lastPaint[lastPaintCounter]).editText);
                                } else {
                                    lastPaintCounter = LAST_PAINT_COUNTER_INIT;
                                }


                            }
                            mutex1.unlock();
                        }
                    }
                });


                editText.setOnTouchListener(new View.OnTouchListener() {
int x;
                    @Override
                    public boolean onTouch(View v, MotionEvent event)//for long touch for help
                    {
                        if (event.getAction() == MotionEvent.ACTION_DOWN) {
                            T = new Timer();
                            TT = new TimerTask() {

                                @Override
                                public void run() {
                                    touchCounter++;
                                    Log.d("counter1", touchCounter + "");
                                    if (touchCounter == 2) {
                                        if (showAlertDialogInterface != null) {
                                            showAlertDialogInterface.showAlertDialog(tashchezCell);
                                            touchCounter = 0;
                                            T.cancel();
                                        }
                                    }
                                }
                            };


                            T.scheduleAtFixedRate(TT, 0, 1000);
                        }

                        if (event.getAction() == MotionEvent.ACTION_UP) {
                            Log.d("counter2", touchCounter + "");
                            touchCounter = 0;
                            T.cancel();

                        }
                        return false;
                    }
                });


                break;
        }

        return convertView;
    }


    //solveModeOn:
    //a - make the definition show on the top of the board.
    //b - //make the cover of intelligNET disappear.
    //c - //paint the answers cells that connected to that particular definition that just clicked.
    //d - set the cursor in the first cell of answer to that particular definition that just clicked.
    private boolean solveModeOn(final TypeTashchezCell tashchezCell, int solveOrDef, int solveClickNum) {

        final TypeTashchezCell cell;

        if(solveOrDef == SOLVE_CLICK) //if this is solve click need to find the "def cell"
        {
            int defCell = findDef(tashchezCell, solveClickNum);
            Log.d("04.07", "defCell: "+defCell);
            cell = getItem(defCell);
        }
        else
            cell = tashchezCell;

        //make the definition show on the top of the board
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
        if(lastPaint[0] != INIT) {
            if (solveOrDef == DEF_CLICK) {
                j = 0;
                //in case that the cell is fill already
                while (j < lastPaint.length && lastPaint[j] != INIT && getItem(lastPaint[j]).editText.getText().length() != 0)
                    j++;

                rKeyboard = new Runnable() {
                    @Override
                    public void run() {
                        if (j < lastPaint.length && lastPaint[j] != INIT && getItem(lastPaint[j]).editText.getText().length() == 0) {
                            getItem(lastPaint[j]).editText.setFocusable(true);
                            setFocusDefClick = false;
                            getItem(lastPaint[j]).editText.requestFocus();
                            setFocusDefClick = true;
                            imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                            imm.showSoftInput(getItem(lastPaint[j]).editText, 0);
                        }
                    }
                };

            }
            else if(solveOrDef == SOLVE_CLICK)
            {
                j = 0;
                //in case that the cell is fill already
                while (j < lastPaint.length && lastPaint[j] != INIT && getItem(lastPaint[j]).editText.getText().length() != 0)
                    j++;

                rKeyboard = new Runnable() {
                    @Override
                    public void run() {
                        getItem(tashchezCell.getIndex()).editText.setFocusable(true);
                        setFocusDefClick = false;
                        getItem(tashchezCell.getIndex()).editText.requestFocus();
                        setFocusDefClick = true;
                        imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);//
                        imm.showSoftInput(getItem(tashchezCell.getIndex()).editText, 0);
                    }
                };
            }
        }

        return true;
    }


    private void paintAnswer(TypeTashchezCell typeTashchezCell)//paint the answer cells from white to red
    {
        int j = 0, i = 0;
        int toPaint = typeTashchezCell.index;

        while (i < lastPaint.length && lastPaint[i] != INIT)//make the lastPaint cells back to white before painting the next answer cells
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
            lastPaint[j] = INIT;

    }



    private int findDef(final TypeTashchezCell tashchezCell, int solveClickNum)
    {
        int defIndex =  tashchezCell.getIndex();

        if(tashchezCell.getCellType().contains("definition"))
        {
            return tashchezCell.getIndex();
        }
        else {
            if ((solveClickNum == FIRST_CLICK_SOLVE && (defIndex + 1) < (TashchezUI.NUM_COL * TashchezUI.NUM_ROW) && !(getItem(defIndex + 1).getCellType().contains("definition"))) ||
                    (solveClickNum == FIRST_CLICK_SOLVE && defIndex == ((TashchezUI.NUM_COL * TashchezUI.NUM_ROW) - 1) && !(getItem(defIndex - 1).getCellType().contains("definition"))) ||
                    ((defIndex + TashchezUI.NUM_COL) < (TashchezUI.NUM_COL * TashchezUI.NUM_ROW) && (defIndex - TashchezUI.NUM_COL) >= 0 && getItem(defIndex + TashchezUI.NUM_COL).getCellType().contains("definition")&& getItem(defIndex - TashchezUI.NUM_COL).getCellType().contains("definition")) ||
                    (defIndex >= 0 && defIndex < TashchezUI.NUM_COL && (defIndex + TashchezUI.NUM_COL) < (TashchezUI.NUM_COL * TashchezUI.NUM_ROW) && getItem(defIndex + TashchezUI.NUM_COL).getCellType().contains("definition")) ||
                    (defIndex >= TashchezUI.NUM_COL*(TashchezUI.NUM_ROW - 1) && defIndex < TashchezUI.NUM_COL*TashchezUI.NUM_ROW && (defIndex - TashchezUI.NUM_COL) >= 0 && getItem(defIndex - TashchezUI.NUM_COL).getCellType().contains("definition")))
            {


                while (getItem(defIndex).getCellType().contains("solve") && defIndex % TashchezUI.NUM_ROW != 0)//go to the right till def cell or till te first cell in the line
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
            else if (solveClickNum == SECOND_CLICK_SOLVE || ((defIndex + 1) < (TashchezUI.NUM_COL * TashchezUI.NUM_ROW) && (getItem(defIndex + 1).getCellType().contains("definition"))))//second click on answer cell paint the vertical or every time it has to be vertical such as "def cell" at the left
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

            }
        }

        return defIndex;
    }


    public void setShowAlertInterface(ShowAlertDialogInterface pass) {
        showAlertDialogInterface = pass;
    }


}
