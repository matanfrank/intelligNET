package com.example.matan.intellignet;

import android.app.Activity;
import android.content.Context;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.text.method.DigitsKeyListener;
import android.text.method.KeyListener;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.concurrent.Semaphore;
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
    private int slvLayout;
    private int defLayout;
    private LayoutInflater inflater;
    private InputMethodManager imm;
    private newEditText editText;
    //private TashchezPassEditText tashchezPassEditText;
    private ImageView coverImage;
    private TextView connectedName;
    private TextView definitionTextView;
   // private RelativeLayout coverLayout;
    public static int j=0;

    public static Activity context;
    private int[] lastPaint;
    private Integer lastPaintCounter = new Integer(0);

    private static boolean setFocusDefClick = false;
    //public static Semaphore mutex = new Semaphore(100, true);
    private static boolean EditTextListenerCall;
    private static boolean onKeyListenerCall = true;
    public static android.os.Handler hKeyboard = new android.os.Handler();
    public static Runnable rKeyboard = null;
    private boolean clearFlag = false;


    public TashchezAdapter(Activity activity, int defLayout, int slvLayout, ArrayList data){//, TashchezPassEditText tashchezPassEditText) {
        super(activity, slvLayout, data);
        this.slvLayout = slvLayout;
        this.defLayout = defLayout;
        inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //this.tashchezPassEditText = tashchezPassEditText;
        coverImage = (ImageView) activity.findViewById(R.id.symbolImage);
        connectedName = (TextView)activity.findViewById(R.id.connectedName);
        definitionTextView = (TextView)activity.findViewById(R.id.definitionTextView);
        //coverLayout = (RelativeLayout)activity.findViewById(R.id.coverLayout);
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
                    @Override
                    public void onClick(View v) {

                        android.os.Handler hDef = new android.os.Handler();

                        Runnable rDef = new Runnable() {
                            @Override
                            public void run() {

                                definitionTextView.setVisibility(View.VISIBLE);
                                definitionTextView.setText(tashchezCell.content);

                                if(rKeyboard!=null)
                                    hKeyboard.postDelayed(rKeyboard, 200);
                            }
                        };


                        TashchezUI.coverLayout.setVisibility(View.GONE);


                        hDef.postDelayed(rDef, 300);


//                        connectedName.setVisibility(View.GONE);
//                        coverImage.setVisibility(View.GONE);


                        paintAnswer(tashchezCell);
                        TashchezUI.solveMode = true;

                        setFocusDefClick = true;}
                }
                );


                imageView = (ImageView) convertView.findViewById(R.id.tashchez_image_def);
                imageView.setImageResource(tashchezCell.background);
                break;


            case SOLVE:

                editText = (newEditText) convertView.findViewById(R.id.tashchez_edit_text);

//                editText.setKeyListener(new DigitsKeyListener(true,true));
//                editText.setInputType(InputType.TYPE_CLASS_TEXT);
                tashchezCell.setEditText(editText);

                //tashchezPassEditText.setEditText(editText);

                //lastPaint[0] is the first cell in the answer word.
                //this "if" is to put the cursor in the first cell of answer when the user click on definition
                if (setFocusDefClick && lastPaint[0] != INIT && TashchezUI.solveMode)
                {
                        j=0;
                        while(j<lastPaint.length && lastPaint[j] != INIT  && getItem(lastPaint[j]).editText.getText().length() != 0)//in case that the cell is fill already
                        {
                            j++;
                        }

                    Log.d("setFocusDefClick1", ""+ setFocusDefClick);


                        rKeyboard = new Runnable() {
                            @Override
                            public void run() {
                                if (j < lastPaint.length && lastPaint[j] != INIT && getItem(lastPaint[j]).editText.getText().length() == 0) {
                                    Log.d("333333", "last: " + lastPaint[j]);
                                    Log.d("setFocusDefClick2", ""+ setFocusDefClick);
                                    getItem(lastPaint[j]).editText.setFocusable(true);
                                    Log.d("setFocusDefClick3", "" + setFocusDefClick);
                                    getItem(lastPaint[j]).editText.requestFocus();
                                    Log.d("setFocusDefClick4", "" + setFocusDefClick);
                                    imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);//
                                    imm.showSoftInput(getItem(lastPaint[j]).editText, 0);
                                    Log.d("setFocusDefClick5", "" + setFocusDefClick);
                                }
                            }
                        };
                    Log.d("setFocusDefClick6", ""+ setFocusDefClick);
                    setFocusDefClick = false;
                    Log.d("setFocusDefClick7", ""+ setFocusDefClick);
                }




                //when user writing an answer the cursor has to jump automatically between cells
                editText.addTextChangedListener(new TextWatcher() {

                    private final Lock mutex1 = new ReentrantLock(true);

                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                        Log.d("lastPaint", "beforeTextChanged\n");
                        if(!clearFlag) {
                            EditTextListenerCall = true;
                            Log.d("iiiiiiii4", lastPaintCounter + "");
                            if (lastPaintCounter == LAST_PAINT_COUNTER_INIT)
                                lastPaintCounter = 0;

                            Log.d("iiiiiiii5", lastPaintCounter + "");
                        }



                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
//                        Log.d("lastPaint", "onTextChanged\n");
//                        Log.d("iiiiiiii6", lastPaintCounter + "");
                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        if (!clearFlag) {
                            final StringBuilder sb = new StringBuilder(s.length());
                            sb.append(s);
                            Log.d("charsss", sb.toString());

                            mutex1.lock();//in case of race condition because sometimes run 3 times

                            Log.d("iiiiiiii7", lastPaintCounter + "");
                            if (EditTextListenerCall) {

                                for (int i = 0; i < lastPaint.length; i++)//to know where the cursor right now
                                    if (lastPaint[i] != INIT && getItem(lastPaint[i]).editText.hasFocus())
                                        lastPaintCounter = i;
                                Log.d("iiiiiiii8", lastPaintCounter + "");

                                Log.d("lastPaint", "afterTextChanged\n");
                                EditTextListenerCall = false;


                                if ((lastPaintCounter + 1) < lastPaint.length && lastPaint[lastPaintCounter + 1] != INIT) {
                                    Log.d("iiiiiiii9", lastPaintCounter + "");
                                    lastPaintCounter += 1;
                                    Log.d("lastPaint", "2: " + lastPaintCounter + "\n");
                                    while ((lastPaintCounter + 1) < lastPaint.length && lastPaint[lastPaintCounter + 1] != INIT &&
                                            getItem(lastPaint[lastPaintCounter]).editText.getText().length() != 0)//in case that the cell is fill already
                                    {
                                        Log.d("lastPaint", "h: " + getItem(lastPaint[lastPaintCounter]).editText.getText().toString());
                                        lastPaintCounter += 1;
                                    }
                                    Log.d("iiiiiiii10", lastPaintCounter + "");

                                    getItem(lastPaint[lastPaintCounter]).editText.requestFocus();
                                    imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);//
                                    imm.showSoftInput(getItem(lastPaint[j]).editText, 0);
                                    imm.restartInput(getItem(lastPaint[lastPaintCounter]).editText);
                                    Log.d("iiiiiiii11", lastPaintCounter + "");
                                } else {
                                    lastPaintCounter = LAST_PAINT_COUNTER_INIT;
                                    Log.d("iiiiiiii12", lastPaintCounter + "");
                                }
                                //Log.d("lastPaint", lastPaintCounter + "\n");


                            }
                            mutex1.unlock();
                        }
                    }
                });



                imageView = (ImageView) convertView.findViewById(R.id.tashchez_image_solve);
                if (!tashchezCell.onEdit)
                    imageView.setImageResource(tashchezCell.background);
                else
                    imageView.setImageResource(R.drawable.ic_solve_sign);

                //when getting into solveMode the coverImage need to disappear when click on solve cell
                editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View v, boolean hasFocus) {
                        Log.d("setFocusDefClick8", ""+ setFocusDefClick);
                        if (hasFocus && setFocusDefClick) {
                            TashchezUI.solveMode = true;


                            android.os.Handler hDef = new android.os.Handler();

                            Runnable rDef = new Runnable() {
                                @Override
                                public void run() {

                                    definitionTextView.setVisibility(View.VISIBLE);
                                    definitionTextView.setText(tashchezCell.content);

                                    if(rKeyboard!=null)
                                        hKeyboard.postDelayed(rKeyboard, 200);
                                }
                            };


                            TashchezUI.coverLayout.setVisibility(View.GONE);


                            hDef.postDelayed(rDef, 300);


//                        connectedName.setVisibility(View.GONE);
//                        coverImage.setVisibility(View.GONE);


                            paintAnswer(tashchezCell);
                            TashchezUI.solveMode = true;
                            setFocusDefClick = true;

                            imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);//
                            imm.showSoftInput(getItem(position).editText, 0);
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
                                    Log.d("iiiiiiii111", "" + lastPaintCounter);
                                    getItem(lastPaint[lastPaintCounter]).editText.requestFocus();
                                    clearFlag = true;
                                    Log.d("iiiiiiii111", "" + lastPaintCounter);
                                    getItem(lastPaint[lastPaintCounter]).editText.getText().clear();
                                    clearFlag = false;
                                }

//                                if (lastPaintCounter - 1 >= 0) {
//                                    lastPaintCounter -= 1;
//                                    Log.d("iiiiiiii222", "" + lastPaintCounter);
//                                    getItem(lastPaint[lastPaintCounter]).editText.requestFocus();
//                                    Log.d("iiiiiiii222", "" + lastPaintCounter);
//                                }

                                if (lastPaintCounter == LAST_PAINT_COUNTER_INIT) {//last cell to erase (the different is jumping back)
                                    int i;
                                    for (i = 0; i < lastPaint.length && lastPaint[i] != INIT; i++)
                                        ;
                                    i--;
                                    Log.d("iiiiiiii1", i + " " + lastPaintCounter);
                                    clearFlag = true;
                                    getItem(lastPaint[i]).editText.getText().clear();
                                    clearFlag = false;
//                                    i -= 1;
                                    Log.d("iiiiiiii2", i + " " + lastPaintCounter);
//                                    getItem(lastPaint[i]).editText.requestFocus();
                                    lastPaintCounter = i;
                                    Log.d("iiiiiiii3", i + " " + lastPaintCounter);

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
                    @Override
                    public void onClick(View v) {
                        setFocusDefClick = false;


                        editText.setFocusable(true);

                        editText.requestFocus();

                        imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);//
                        imm.showSoftInput(editText, 0);

                        Log.d("setFocusDefClick"," here");
                    }
                });



                break;
        }

        return convertView;
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


}
